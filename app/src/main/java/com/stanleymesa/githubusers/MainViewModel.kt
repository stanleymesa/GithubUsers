package com.stanleymesa.githubusers

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stanleymesa.core.datastore.AppDataStoreKeys
import com.stanleymesa.core.enums.Themes
import com.stanleymesa.core.enums.toThemeEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : ViewModel() {

    private var previousTheme: Themes? = runBlocking {
        dataStore.data.first()[AppDataStoreKeys.KEY_THEME]?.toThemeEnum()
            ?: Themes.LIGHT
    }

    val themesSetting: StateFlow<Themes> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[AppDataStoreKeys.KEY_THEME]?.toThemeEnum() ?: previousTheme ?: Themes.LIGHT
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), previousTheme ?: Themes.LIGHT)

    var state = MutableStateFlow(MainState())
        private set

    private var snackbarJob: Job? = null

//    init {
//        viewModelScope.launch {
//            delay(1400L)
//            _isSplashDone.value = true
//        }
//    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.SetSnackbar -> state.update {
                it.copy(
                    snackbar = state.value.snackbar.copy(
                        message = event.snackbarState.message,
                        messageId = event.snackbarState.messageId,
                        isSuccess = event.snackbarState.isSuccess,
                    )
                )
            }

            is MainEvent.ResetSnackbar -> resetSnackbar(event.isDelay)
        }
    }

    private fun resetSnackbar(isDelay: Boolean = true) {
        snackbarJob?.cancel()
        snackbarJob = viewModelScope.launch {
            if (isDelay) {
                delay(3000L)
            }
            state.update {
                it.copy(snackbar = it.snackbar.copy(message = null, messageId = null))
            }
        }
    }

}
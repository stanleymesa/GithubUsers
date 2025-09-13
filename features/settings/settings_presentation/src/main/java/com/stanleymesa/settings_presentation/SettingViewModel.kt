package com.stanleymesa.settings_presentation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
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
class SettingViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : ViewModel() {

    private var previousTheme: Themes? = runBlocking {
        dataStore.data.first()[AppDataStoreKeys.KEY_THEME]?.toThemeEnum()
            ?: Themes.LIGHT
    }

    val themesSetting: StateFlow<Themes> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[AppDataStoreKeys.KEY_THEME]?.toThemeEnum() ?: previousTheme ?: Themes.LIGHT
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), previousTheme ?: Themes.LIGHT)

    var state = MutableStateFlow(SettingState())
        private set

    private var job: Job? = null
    private var snackbarJob: Job? = null

    fun onEvent(event: SettingEvent) {
        when (event) {
            is SettingEvent.SwitchDarkMode -> {
                state.value = state.value.copy(isDarkMode = event.isSwitchOn)
                onThemeChange(returnTheme())
            }

            is SettingEvent.SwitchDynamicMode -> {
                state.value = state.value.copy(isDynamicMode = event.isSwitchOn)
                onThemeChange(returnTheme())
            }

            is SettingEvent.ResetSnackbar -> resetSnackbar(event.isDelay)

            is SettingEvent.SetSnackbar -> state.update {
                it.copy(snackbar = event.snackbarState)
            }

        }
    }

    private fun returnTheme() = when {
        state.value.isDynamicMode && state.value.isDarkMode -> Themes.DYNAMIC_DARK
        state.value.isDynamicMode && !state.value.isDarkMode -> Themes.DYNAMIC_LIGHT
        !state.value.isDynamicMode && state.value.isDarkMode -> Themes.DARK
        else -> Themes.LIGHT
    }

    private fun onThemeChange(themes: Themes) = viewModelScope.launch {
        dataStore.edit { setting ->
            setting[AppDataStoreKeys.KEY_THEME] = themes.toString()
        }
    }

    private fun debounce(action: () -> Unit) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(500)
            action.invoke()
        }
    }

    private fun resetSnackbar(isDelay: Boolean = true) {
        snackbarJob?.cancel()
        snackbarJob = viewModelScope.launch {
            if (isDelay) {
                delay(3000L)
            }
            state.update {
                it.copy(
                    snackbar = it.snackbar.copy(message = null, messageId = null)
                )
            }
        }
    }

}
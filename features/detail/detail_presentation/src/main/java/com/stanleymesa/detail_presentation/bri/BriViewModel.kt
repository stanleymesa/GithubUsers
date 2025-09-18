package com.stanleymesa.detail_presentation.bri

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stanleymesa.core.util.NetworkHelper
import com.stanleymesa.detail_domain.use_case.DetailUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BriViewModel @Inject constructor(
    private val useCases: DetailUseCases,
    private val dataStore: DataStore<Preferences>,
    private val networkHelper: NetworkHelper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    var state = MutableStateFlow(BriState())
        private set

    private var job: Job? = null
    private var snackbarJob: Job? = null
    private var refreshJob: Job? = null

    fun onEvent(event: BriEvent) {
        when (event) {
            is BriEvent.ResetSnackbar -> resetSnackbar(event.isDelay)

            is BriEvent.SetSnackbar -> state.update {
                it.copy(
                    snackbar = state.value.snackbar.copy(
                        message = event.snackbarState.message,
                        messageId = event.snackbarState.messageId,
                        isSuccess = event.snackbarState.isSuccess,
                    )
                )
            }

            is BriEvent.SetLoading -> state.update { it.copy(isLoading = event.isLoading) }

            is BriEvent.SetRefreshing -> state.update { it.copy(isRefreshing = event.isRefreshing) }
        }
    }

    private fun debounce(delay: Long = 250, action: () -> Unit) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.Main) {
            delay(delay)
            action.invoke()
        }
    }

    private fun resetSnackbar(isDelay: Boolean = true) {
        snackbarJob?.cancel()
        snackbarJob = viewModelScope.launch {
            if (isDelay) {
                delay(3000L)
            }
            state.value = state.value.copy(
                snackbar = state.value.snackbar.copy(message = null, messageId = null)
            )
        }
    }

}
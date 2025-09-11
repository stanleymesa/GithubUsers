package com.stanleymesa.detail_presentation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.stanleymesa.core.route.DetailRoute
import com.stanleymesa.core.util.NetworkHelper
import com.stanleymesa.core.util.extentions.loge
import com.stanleymesa.core.util.extentions.toJsonPretty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
//    private val useCases: AnnouncementUseCases,
    private val dataStore: DataStore<Preferences>,
    private val networkHelper: NetworkHelper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val route = savedStateHandle.toRoute<DetailRoute>()

    var state = MutableStateFlow(DetailState())
        private set

    private var job: Job? = null
    private var snackbarJob: Job? = null
    private var refreshJob: Job? = null

    init {
        loge("detail route = ${route.toJsonPretty()}")
    }

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.ResetSnackbar -> resetSnackbar(event.isDelay)

            is DetailEvent.SetSnackbar -> state.update {
                it.copy(
                    snackbar = state.value.snackbar.copy(
                        message = event.snackbarState.message,
                        messageId = event.snackbarState.messageId,
                        isSuccess = event.snackbarState.isSuccess,
                    )
                )
            }

            is DetailEvent.SetLoading -> state.update { it.copy(isLoading = event.isLoading) }

            is DetailEvent.SetRefreshing -> state.update { it.copy(isRefreshing = event.isRefreshing) }

            is DetailEvent.SetLinearLoading -> state.update { it.copy(isLinearLoading = event.isLoading) }

            is DetailEvent.OnSearchChange -> {
                state.update { it.copy(search = event.search) }
            }
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
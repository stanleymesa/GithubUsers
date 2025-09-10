package com.stanleymesa.search_presentation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stanleymesa.core.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
//    private val useCases: AnnouncementUseCases,
    private val dataStore: DataStore<Preferences>,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    var state = MutableStateFlow(SearchState())
        private set

    private var job: Job? = null
    private var snackbarJob: Job? = null
    private var refreshJob: Job? = null

//    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
//    val announcements = state.value.payload.debounce { 250 }.flatMapLatest { payload ->
//        useCases.getAnnouncementPaging(payload)
//    }.cachedIn(viewModelScope)
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.ResetSnackbar -> resetSnackbar(event.isDelay)

            is SearchEvent.SetSnackbar -> state.update {
                it.copy(
                    snackbar = state.value.snackbar.copy(
                        message = event.snackbarState.message,
                        messageId = event.snackbarState.messageId,
                        isSuccess = event.snackbarState.isSuccess,
                    )
                )
            }

            is SearchEvent.SetLoading -> state.update { it.copy(isLoading = event.isLoading) }

            is SearchEvent.SetRefreshing -> state.update { it.copy(isRefreshing = event.isRefreshing) }

            is SearchEvent.SetLinearLoading -> state.update { it.copy(isLinearLoading = event.isLoading) }

            is SearchEvent.OnSearchChange -> {
                state.update { it.copy(search = event.search) }
//                debounce {
//                    state.value.payload.update {
//                        it.copy(search = event.search)
//                    }
//                }
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

    private fun handleRefreshNoInternet() {
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch(Dispatchers.IO) {
            onEvent(SearchEvent.SetRefreshing(true))
            delay(1000L)
            onEvent(SearchEvent.SetRefreshing(false))
        }
    }
}
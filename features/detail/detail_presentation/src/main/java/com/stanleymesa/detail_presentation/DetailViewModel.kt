package com.stanleymesa.detail_presentation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.stanleymesa.core.R
import com.stanleymesa.core.network.Resource
import com.stanleymesa.core.route.DetailRoute
import com.stanleymesa.core.util.NetworkHelper
import com.stanleymesa.core.util.SnackbarState
import com.stanleymesa.detail_domain.use_case.DetailUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCases: DetailUseCases,
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
        getUser(route.username)
        getUserRepos(route.username)
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

        }
    }

    private fun getUser(username: String) = viewModelScope.launch(Dispatchers.IO) {
        onEvent(DetailEvent.SetLoading(true))
        useCases.getUser(username).collectLatest { resource ->
            when (resource) {
                is Resource.Success -> {
                    state.update { it.copy(user = resource.data) }
                }

                else -> {
                    resource.data?.let { user -> state.update { it.copy(user = user) } }
                    onEvent(
                        DetailEvent.SetSnackbar(
                            if (networkHelper.isNetworkConnected()) {
                                SnackbarState(
                                    message = resource.message,
                                    isSuccess = false
                                )
                            } else {
                                SnackbarState(
                                    messageId = R.string.please_check_internet_connection,
                                    isSuccess = false
                                )
                            }

                        )
                    )
                }
            }
        }
        onEvent(DetailEvent.SetLoading(false))
    }

    private fun getUserRepos(username: String) = viewModelScope.launch(Dispatchers.IO) {
        onEvent(DetailEvent.SetLoading(true))
        useCases.getUserRepos(username).collectLatest { resource ->
            when (resource) {
                is Resource.Success -> {
                    state.update { it.copy(userRepos = resource.data.orEmpty()) }
                }

                else -> {
                    resource.data?.let { userRepos -> state.update { it.copy(userRepos = userRepos) } }
                    onEvent(
                        DetailEvent.SetSnackbar(
                            if (networkHelper.isNetworkConnected()) {
                                SnackbarState(
                                    message = resource.message,
                                    isSuccess = false
                                )
                            } else {
                                SnackbarState(
                                    messageId = R.string.please_check_internet_connection,
                                    isSuccess = false
                                )
                            }

                        )
                    )
                }
            }
        }
        onEvent(DetailEvent.SetLoading(false))
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
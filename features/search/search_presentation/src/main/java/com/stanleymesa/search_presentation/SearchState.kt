package com.stanleymesa.search_presentation

import com.stanleymesa.core.util.SnackbarState
import com.stanleymesa.search_domain.model.UserPayload
import kotlinx.coroutines.flow.MutableStateFlow

data class SearchState(
    val snackbar: SnackbarState = SnackbarState(),
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val isLinearLoading: Boolean = false,

    val payload: MutableStateFlow<UserPayload> = MutableStateFlow(UserPayload()),
    val notLoadingCount: Int = 0,
    val search: String = "",
)
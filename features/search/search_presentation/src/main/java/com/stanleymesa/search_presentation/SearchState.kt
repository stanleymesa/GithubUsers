package com.stanleymesa.search_presentation

import com.stanleymesa.core.util.SnackbarState

data class SearchState(
    val snackbar: SnackbarState = SnackbarState(),
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val isLinearLoading: Boolean = false,

    val notLoadingCount: Int = 0,
    val search: String = "",
)
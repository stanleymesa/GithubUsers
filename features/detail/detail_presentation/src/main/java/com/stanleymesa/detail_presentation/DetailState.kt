package com.stanleymesa.detail_presentation

import com.stanleymesa.core.util.SnackbarState

data class DetailState(
    val snackbar: SnackbarState = SnackbarState(),
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val isLinearLoading: Boolean = false,

    val notLoadingCount: Int = 0,
    val search: String = "",
)
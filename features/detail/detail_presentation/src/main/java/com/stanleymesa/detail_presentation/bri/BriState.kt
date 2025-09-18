package com.stanleymesa.detail_presentation.bri

import com.stanleymesa.core.util.SnackbarState

data class BriState(
    val snackbar: SnackbarState = SnackbarState(),
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    )
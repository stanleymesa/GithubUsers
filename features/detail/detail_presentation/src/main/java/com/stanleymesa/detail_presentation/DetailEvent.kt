package com.stanleymesa.detail_presentation

import com.stanleymesa.core.util.SnackbarState


sealed class DetailEvent {
    data class SetSnackbar(val snackbarState: SnackbarState) : DetailEvent()
    data class ResetSnackbar(val isDelay: Boolean = true) : DetailEvent()
    data class SetLoading(val isLoading: Boolean) : DetailEvent()
    data class SetRefreshing(val isRefreshing: Boolean) : DetailEvent()

}
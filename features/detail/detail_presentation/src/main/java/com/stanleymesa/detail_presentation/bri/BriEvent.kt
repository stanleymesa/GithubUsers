package com.stanleymesa.detail_presentation.bri

import com.stanleymesa.core.util.SnackbarState


sealed class BriEvent {
    data class SetSnackbar(val snackbarState: SnackbarState) : BriEvent()
    data class ResetSnackbar(val isDelay: Boolean = true) : BriEvent()
    data class SetLoading(val isLoading: Boolean) : BriEvent()
    data class SetRefreshing(val isRefreshing: Boolean) : BriEvent()

}
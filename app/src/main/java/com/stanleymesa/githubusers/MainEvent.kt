package com.stanleymesa.githubusers

import com.stanleymesa.core.util.SnackbarState

sealed class MainEvent {
    data class SetSnackbar(val snackbarState: SnackbarState) : MainEvent()
    data class ResetSnackbar(val isDelay: Boolean = true) : MainEvent()
}
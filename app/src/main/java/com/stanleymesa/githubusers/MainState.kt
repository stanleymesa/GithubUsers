package com.stanleymesa.githubusers

import com.stanleymesa.core.util.SnackbarState

data class MainState(
    val snackbar: SnackbarState = SnackbarState(),
    val showNotificationPermissionDialog: Pair<Boolean, Boolean> = Pair(false, false) // isShow, isRationale
)
package com.stanleymesa.settings_presentation

import android.os.Build
import com.stanleymesa.core.util.SnackbarState

data class SettingState(
    val snackbar: SnackbarState = SnackbarState(),
    val isDarkMode: Boolean = false,
    val isDynamicMode: Boolean = false,
    val isRestored: Boolean = false,
    val isDynamicVisible: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
)
package com.stanleymesa.settings_presentation

import com.stanleymesa.core.util.SnackbarState

sealed class SettingEvent {
    data class SwitchDarkMode(val isSwitchOn: Boolean) : SettingEvent()
    data class SwitchDynamicMode(val isSwitchOn: Boolean) : SettingEvent()
    data class SetSnackbar(val snackbarState: SnackbarState) : SettingEvent()
    data class ResetSnackbar(val isDelay: Boolean = true) : SettingEvent()
}
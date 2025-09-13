package com.stanleymesa.githubusers.route

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.stanleymesa.core.enums.Themes
import com.stanleymesa.settings_presentation.SettingScreen
import com.stanleymesa.core.route.SettingsRoute
import com.stanleymesa.core.util.extentions.loge
import com.stanleymesa.settings_presentation.SettingViewModel
import kotlinx.coroutines.flow.collectLatest

fun NavGraphBuilder.settingsRoute(
    navHostController: NavHostController,
) = composable<SettingsRoute> { _ ->
    val viewModel: SettingViewModel = hiltViewModel()
    val isRestored = viewModel.state.collectAsState().value.isRestored

    /** Restore the theme settings when the screen is first launched */
    if (!isRestored) {
        LaunchedEffect(viewModel.themesSetting) {
            viewModel.themesSetting.collectLatest {
                loge("themesSetting: $it")
                viewModel.state.value = viewModel.state.value.copy(
                    isDarkMode = it == Themes.DARK || it == Themes.DYNAMIC_DARK,
                    isDynamicMode = it == Themes.DYNAMIC_LIGHT || it == Themes.DYNAMIC_DARK,
                    isRestored = true
                )
            }
        }
    }

    SettingScreen(
        navHostController = navHostController,
        state = viewModel.state.collectAsState().value,
        onEvent = viewModel::onEvent,
    )
}
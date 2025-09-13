package com.stanleymesa.githubusers

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.stanleymesa.core.R
import com.stanleymesa.core.enums.Themes
import com.stanleymesa.core.route.SearchRoute
import com.stanleymesa.core.ui.component.compose.DefaultDialog
import com.stanleymesa.core.ui.theme.GithubTheme
import com.stanleymesa.core.util.extentions.hasPermissions
import com.stanleymesa.core.util.extentions.isFalse
import com.stanleymesa.core.util.extentions.openAppSettings
import com.stanleymesa.githubusers.route.detailRoute
import com.stanleymesa.githubusers.route.searchRoute
import com.stanleymesa.githubusers.route.settingsRoute
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            enableEdgeToEdge()

            setContent {
                val state = viewModel.state.collectAsState().value
                val themeState = viewModel.themesSetting.collectAsState().value
                val isDarkMode = themeState == Themes.DARK || themeState == Themes.DYNAMIC_DARK
                val isDynamic =
                    themeState == Themes.DYNAMIC_LIGHT || themeState == Themes.DYNAMIC_DARK

                val notificationPermissionLauncher =
                    rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                        if (isGranted.isFalse() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            val isShowRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale(
                                    this@MainActivity, Manifest.permission.POST_NOTIFICATIONS
                                )
                            viewModel.onEvent(
                                MainEvent.ShowNotificationPermissionDialog(
                                    true,
                                    isShowRationale
                                )
                            )
                        }
                    }

                /** Check Notification Permission when first open main page */
                LaunchedEffect(Unit) {
                    checkNotificationPermission(
                        this@MainActivity,
                        notificationPermissionLauncher
                    )
                }

                GithubTheme(
                    darkTheme = isDarkMode,
                    dynamicColor = isDynamic,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surface),
                    ) {
                        val navController = rememberNavController()

                        NavHost(
                            navController = navController,
                            startDestination = SearchRoute,
                            enterTransition = { fadeIn(animationSpec = tween(250)) },
                            exitTransition = { fadeOut(animationSpec = tween(250)) },
                            popEnterTransition = { fadeIn(animationSpec = tween(250)) },
                            popExitTransition = { fadeOut(animationSpec = tween(250)) },
                        ) {
                            searchRoute(navController)
                            detailRoute(navController)
                            settingsRoute(navController)
                        }

                        /** Show Notification Permission Dialog */
                        val (isShow, isRationale) = state.showNotificationPermissionDialog
                        if (isShow && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            DefaultDialog(
                                icon = Icons.Default.WarningAmber,
                                title = stringResource(id = R.string.notification_permission),
                                desc = stringResource(id = R.string.notification_permission_desc),
                                positiveButtonText = stringResource(id = R.string.okay),
                                negativeButtonShow = false,
                                onPositive = {
                                    if (isRationale) {
                                        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                    } else {
                                        this@MainActivity.openAppSettings()
                                    }
                                },
                                onDismissRequest = {
                                    viewModel.onEvent(
                                        MainEvent.ShowNotificationPermissionDialog(
                                            false,
                                            isRationale
                                        )
                                    )
                                }
                            )
                        }

                    }
                }
            }
        }
    }

    private fun checkNotificationPermission(
        context: Context,
        notificationPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (hasPermissions(
                    context,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS)
                ).isFalse()
            ) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

}

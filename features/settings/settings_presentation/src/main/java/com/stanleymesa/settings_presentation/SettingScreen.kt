package com.stanleymesa.settings_presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Contrast
import androidx.compose.material.icons.outlined.Cookie
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.stanleymesa.core.R
import com.stanleymesa.core.ui.component.compose.DefaultSnackbar
import com.stanleymesa.core.ui.component.compose.DefaultSpacer
import com.stanleymesa.core.ui.component.compose.DefaultTopAppBar
import com.stanleymesa.core.ui.component.compose.showDefaultSnackbar
import com.stanleymesa.core.ui.theme.LocalDimen
import com.stanleymesa.core.util.extentions.isFalse

@Composable
fun SettingScreen(
    navHostController: NavHostController,
    state: SettingState,
    onEvent: (SettingEvent) -> Unit,
) {
    val snackbarState = remember {
        SnackbarHostState()
    }

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    LaunchedEffect(state.snackbar) {
        if (state.snackbar.message.isNullOrBlank().isFalse() || state.snackbar.messageId != null) {
            onEvent(SettingEvent.ResetSnackbar())
            snackbarState.showDefaultSnackbar(
                context = context,
                snackbar = state.snackbar,
                actionLabel = context.getString(R.string.ok),
            ).apply {
                when (this) {
                    SnackbarResult.Dismissed -> {}
                    SnackbarResult.ActionPerformed -> {
                        onEvent(SettingEvent.ResetSnackbar(false))
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarState,
                snackbar = { DefaultSnackbar(data = it) }
            )
        },
        topBar = {
            DefaultTopAppBar(
                navHostController = navHostController,
                titleId = R.string.settings
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(
                    horizontal = LocalDimen.current.regular,
                    vertical = LocalDimen.current.regular
                )
                .verticalScroll(scrollState),
        ) {
            ThemeSetting(settingState = state, onEvent = onEvent)
            DefaultSpacer(height = LocalDimen.current.extraRegular)
            HorizontalDivider()
        }
    }
}

@Composable
private fun ThemeSetting(
    settingState: SettingState,
    onEvent: (SettingEvent) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = LocalDimen.current.regular,
                    vertical = LocalDimen.current.default
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Outlined.Contrast,
                    contentDescription = stringResource(id = R.string.icon)
                )
                DefaultSpacer(width = LocalDimen.current.extraRegular)
                Column {
                    Text(
                        text = stringResource(id = R.string.dark_mode),
                        style = MaterialTheme.typography.labelMedium
                    )
                    DefaultSpacer(height = LocalDimen.current.small)
                    Text(
                        text = stringResource(id = if (settingState.isDarkMode) R.string.on else R.string.off),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Switch(
                checked = settingState.isDarkMode,
                onCheckedChange = { isSwitchOn ->
                    onEvent(
                        SettingEvent.SwitchDarkMode(isSwitchOn)
                    )
                },
                thumbContent = {
                    if (settingState.isDarkMode) {
                        Icon(
                            Icons.Outlined.DarkMode,
                            contentDescription = stringResource(
                                id = R.string.icon
                            ),
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    } else {
                        Icon(
                            Icons.Outlined.LightMode,
                            contentDescription = stringResource(
                                id = R.string.icon
                            ),
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                },
            )
        }
    }
    if (settingState.isDynamicVisible) {
        DefaultSpacer()
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = LocalDimen.current.regular,
                        vertical = LocalDimen.current.default
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.Cookie,
                        contentDescription = stringResource(id = R.string.icon)
                    )
                    DefaultSpacer(width = LocalDimen.current.extraRegular)
                    Column {
                        Text(
                            text = stringResource(id = R.string.dynamic),
                            style = MaterialTheme.typography.labelMedium
                        )
                        DefaultSpacer(height = LocalDimen.current.small)
                        Text(
                            text = stringResource(id = if (settingState.isDynamicMode) R.string.on else R.string.off),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Switch(
                    checked = settingState.isDynamicMode,
                    onCheckedChange = { isSwitchOn ->
                        onEvent(
                            SettingEvent.SwitchDynamicMode(isSwitchOn)
                        )
                    },
                )
            }
        }
    }
}
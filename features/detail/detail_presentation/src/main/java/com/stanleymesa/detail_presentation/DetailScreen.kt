package com.stanleymesa.detail_presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.navigation.NavHostController
import com.stanleymesa.core.ui.component.compose.DefaultProgress
import com.stanleymesa.core.ui.component.compose.DefaultSnackbar
import com.stanleymesa.core.ui.component.compose.showDefaultSnackbar
import com.stanleymesa.core.util.extentions.isFalse
import com.stanleymesa.core.util.extentions.isTrue

@Composable
fun DetailScreen(
    navHostController: NavHostController,
    state: DetailState,
    onEvent: (DetailEvent) -> Unit,
) {
    val snackbarState = remember {
        SnackbarHostState()
    }
    val context = LocalContext.current
    val view = LocalView.current

    LaunchedEffect(state.snackbar) {
        if (!state.snackbar.message.isNullOrBlank() || state.snackbar.messageId != null) {
            onEvent(DetailEvent.ResetSnackbar())
            snackbarState.showDefaultSnackbar(
                context = context,
                snackbar = state.snackbar,
                actionLabel = context.getString(com.stanleymesa.core.R.string.ok),
            ).apply {
                when (this) {
                    SnackbarResult.Dismissed -> {}
                    SnackbarResult.ActionPerformed -> {
                        onEvent(DetailEvent.ResetSnackbar(false))
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        bottomBar = {
            if (state.isLinearLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    trackColor = MaterialTheme.colorScheme.surface
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarState,
                snackbar = { DefaultSnackbar(data = it) },
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Box(modifier = Modifier.padding(it)) {
            Text("Detail")
        }

        if (state.isLoading.isTrue() && state.isRefreshing.isFalse()) {
            DefaultProgress(
                modifier = Modifier.fillMaxSize()
            )
        }

    }
}

package com.stanleymesa.search_presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.stanleymesa.core.util.extentions.findActivity
import com.stanleymesa.core.util.extentions.isFalse
import com.stanleymesa.core.util.extentions.isTrue

@Composable
fun SearchScreen(
    navHostController: NavHostController,
    state: SearchState,
    onEvent: (SearchEvent) -> Unit,
) {
    val snackbarState = remember {
        SnackbarHostState()
    }
    val context = LocalContext.current
    val activity = context.findActivity()
    val view = LocalView.current
    val lazyListState = rememberLazyListState()

    LaunchedEffect(state.snackbar) {
        if (!state.snackbar.message.isNullOrBlank() || state.snackbar.messageId != null) {
            onEvent(SearchEvent.ResetSnackbar())
            snackbarState.showDefaultSnackbar(
                context = context,
                snackbar = state.snackbar,
                actionLabel = context.getString(com.stanleymesa.core.R.string.ok),
            ).apply {
                when (this) {
                    SnackbarResult.Dismissed -> {}
                    SnackbarResult.ActionPerformed -> {
                        onEvent(SearchEvent.ResetSnackbar(false))
                    }
                }
            }
        }
    }

//    LaunchedEffect(lazyPagingItems.loadState.refresh) {
//        when (lazyPagingItems.loadState.refresh) {
//            is LoadState.Loading -> {
//                onEvent(SearchEvent.SetLoading(true))
//                onEvent(SearchEvent.SetLinearLoading(true))
//            }
//
//            is LoadState.NotLoading -> {
//                onEvent(SearchEvent.NotLoadingIncrement)
//                onEvent(SearchEvent.SetLoading(false))
//                onEvent(SearchEvent.SetLinearLoading(false))
//                onEvent(SearchEvent.SetRefreshing(false))
//                runCatching { lazyListState.animateScrollToItem(0) }
//            }
//
//            is LoadState.Error -> {
//                onEvent(SearchEvent.NotLoadingIncrement)
//                onEvent(SearchEvent.SetLoading(false))
//                onEvent(SearchEvent.SetLinearLoading(false))
//                onEvent(SearchEvent.SetRefreshing(false))
//
//                val error = getLoadStateError(lazyPagingItems)
//                if (error != null) {
//                    onEvent(
//                        SearchEvent.SetSnackbar(
//                            SnackbarState(
//                                message = if (error.message.equals(R.string.please_check_internet_connection.toString())) context.getString(
//                                    R.string.please_check_internet_connection
//                                ) else error.message,
//                                isSuccess = false
//                            )
//                        )
//                    )
//                }
//            }
//        }
//    }
//
//    LaunchedEffect(lazyPagingItems.loadState.append) {
//        when (lazyPagingItems.loadState.append) {
//            is LoadState.Loading -> {
//                onEvent(SearchEvent.SetLinearLoading(true))
//            }
//
//            is LoadState.NotLoading -> {
//                onEvent(SearchEvent.SetLinearLoading(false))
//            }
//
//            is LoadState.Error -> {
//                onEvent(SearchEvent.SetLinearLoading(false))
//            }
//        }
//    }
//
//    LaunchedEffect(lazyPagingItems.loadState.prepend) {
//        when (lazyPagingItems.loadState.prepend) {
//            is LoadState.Loading -> {
//                onEvent(SearchEvent.SetLinearLoading(true))
//            }
//
//            is LoadState.NotLoading -> {
//                onEvent(SearchEvent.SetLinearLoading(false))
//            }
//
//            is LoadState.Error -> {
//                onEvent(SearchEvent.SetLinearLoading(false))
//            }
//        }
//    }

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
            Text("Search")
        }

        if (state.isLoading.isTrue() && state.isRefreshing.isFalse()) {
            DefaultProgress(
                modifier = Modifier.fillMaxSize()
            )
        }

    }
}

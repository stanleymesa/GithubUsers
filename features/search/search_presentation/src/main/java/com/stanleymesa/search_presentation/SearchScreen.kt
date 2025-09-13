package com.stanleymesa.search_presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.stanleymesa.core.R
import com.stanleymesa.core.route.DetailRoute
import com.stanleymesa.core.route.SettingsRoute
import com.stanleymesa.core.shared_data.model.User
import com.stanleymesa.core.ui.component.compose.BoxWithSwipeRefresh
import com.stanleymesa.core.ui.component.compose.DefaultEmptyState
import com.stanleymesa.core.ui.component.compose.DefaultProgress
import com.stanleymesa.core.ui.component.compose.DefaultSnackbar
import com.stanleymesa.core.ui.component.compose.DefaultSpacer
import com.stanleymesa.core.ui.component.compose.DefaultTextInputSearch
import com.stanleymesa.core.ui.component.compose.DefaultTopAppBar
import com.stanleymesa.core.ui.component.compose.showDefaultSnackbar
import com.stanleymesa.core.ui.theme.LocalDimen
import com.stanleymesa.core.util.SnackbarState
import com.stanleymesa.core.util.extentions.getLoadStateError
import com.stanleymesa.core.util.extentions.isFalse
import com.stanleymesa.core.util.extentions.isTrue
import com.stanleymesa.core.util.extentions.orZero
import com.stanleymesa.search_presentation.component.UserCard

@Composable
fun SearchScreen(
    navHostController: NavHostController,
    state: SearchState,
    onEvent: (SearchEvent) -> Unit,
    lazyPagingItems: LazyPagingItems<User>
) {
    val snackbarState = remember {
        SnackbarHostState()
    }
    val context = LocalContext.current
    val lazyListState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(state.snackbar) {
        if (!state.snackbar.message.isNullOrBlank() || state.snackbar.messageId != null) {
            onEvent(SearchEvent.ResetSnackbar())
            snackbarState.showDefaultSnackbar(
                context = context,
                snackbar = state.snackbar,
                actionLabel = context.getString(R.string.ok),
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

    LaunchedEffect(lazyPagingItems.loadState.refresh) {
        when (lazyPagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                onEvent(SearchEvent.SetLoading(true))
                onEvent(SearchEvent.SetLinearLoading(true))
            }

            is LoadState.NotLoading -> {
                onEvent(SearchEvent.SetLoading(false))
                onEvent(SearchEvent.SetLinearLoading(false))
                onEvent(SearchEvent.SetRefreshing(false))
                runCatching { lazyListState.animateScrollToItem(0) }
            }

            is LoadState.Error -> {
                onEvent(SearchEvent.SetLoading(false))
                onEvent(SearchEvent.SetLinearLoading(false))
                onEvent(SearchEvent.SetRefreshing(false))

                val error = getLoadStateError(lazyPagingItems)
                if (error != null) {
                    onEvent(
                        SearchEvent.SetSnackbar(
                            SnackbarState(
                                message = if (error.message.equals(R.string.please_check_internet_connection.toString())) context.getString(
                                    R.string.please_check_internet_connection
                                ) else error.message,
                                isSuccess = false
                            )
                        )
                    )
                }
            }
        }
    }

    LaunchedEffect(lazyPagingItems.loadState.append) {
        when (lazyPagingItems.loadState.append) {
            is LoadState.Loading -> {
                onEvent(SearchEvent.SetLinearLoading(true))
            }

            is LoadState.NotLoading -> {
                onEvent(SearchEvent.SetLinearLoading(false))
            }

            is LoadState.Error -> {
                onEvent(SearchEvent.SetLinearLoading(false))
            }
        }
    }

    LaunchedEffect(lazyPagingItems.loadState.prepend) {
        when (lazyPagingItems.loadState.prepend) {
            is LoadState.Loading -> {
                onEvent(SearchEvent.SetLinearLoading(true))
            }

            is LoadState.NotLoading -> {
                onEvent(SearchEvent.SetLinearLoading(false))
            }

            is LoadState.Error -> {
                onEvent(SearchEvent.SetLinearLoading(false))
            }
        }
    }

    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        topBar = {
            DefaultTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                navHostController = navHostController,
                titleId = R.string.app_name,
                canBack = false,
                actions = {
                    IconButton(onClick = {
                        navHostController.navigate(SettingsRoute)
                    }) {
                        Icon(Icons.Outlined.Settings, contentDescription = "")
                    }
                }
            )
        },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            DefaultTextInputSearch(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = LocalDimen.current.regular),
                value = state.search,
                placeholder = "Search",
                onValueChange = { search ->
                    onEvent(SearchEvent.OnSearchChange(search))
                },
                onSearch = {
                    keyboardController?.hide()
                },
                onValueClear = {
                    keyboardController?.hide()
                    onEvent(SearchEvent.OnSearchChange(""))
                }
            )
            DefaultSpacer()

            BoxWithSwipeRefresh(
                modifier = Modifier.fillMaxSize(),
                isRefreshing = state.isRefreshing,
                onSwipe = {
                    onEvent(SearchEvent.SetRefreshing(true))
                    lazyPagingItems.refresh()
                }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = lazyListState,
                    contentPadding = PaddingValues(
                        start = LocalDimen.current.regular,
                        end = LocalDimen.current.regular,
                        bottom = LocalDimen.current.extraRegular
                    )
                ) {
                    if (lazyPagingItems.itemCount > 0) {
                        items(
                            count = lazyPagingItems.itemCount,
                            key = lazyPagingItems.itemKey { key -> key.id.orZero() },
                        ) { index ->
                            lazyPagingItems[index]?.let { user ->
                                UserCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .animateItem(),
                                    user = user
                                ) { data ->
                                    navHostController.navigate(DetailRoute(username = data.login))
                                }
                                if (index < lazyPagingItems.itemCount.minus(1)) {
                                    DefaultSpacer(height = LocalDimen.current.medium)
                                }
                            }
                        }
                    } else {
                        if (state.isLoading.isFalse()) {
                            item {
                                DefaultSpacer(height = 120.dp)
                                if (state.search.isBlank()) {
                                    DefaultEmptyState(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .animateItem(),
                                        icon = Icons.Outlined.Search,
                                        title = stringResource(R.string.search_placeholder_initial_title),
                                        message = stringResource(R.string.search_placeholder_initial_subtitle),
                                    )
                                } else {
                                    DefaultEmptyState(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .animateItem(),
                                        icon = Icons.Outlined.Edit,
                                        title = stringResource(R.string.search_placeholder_empty_title),
                                        message = stringResource(R.string.search_placeholder_empty_subtitle),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (state.isLoading.isTrue() && state.isRefreshing.isFalse()) {
            DefaultProgress(
                modifier = Modifier.fillMaxSize()
            )
        }

    }
}

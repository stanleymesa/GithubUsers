package com.stanleymesa.detail_presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.stanleymesa.core.R
import com.stanleymesa.core.ui.component.compose.DefaultProgress
import com.stanleymesa.core.ui.component.compose.DefaultSnackbar
import com.stanleymesa.core.ui.component.compose.DefaultSpacer
import com.stanleymesa.core.ui.component.compose.DefaultTopAppBar
import com.stanleymesa.core.ui.component.compose.showDefaultSnackbar
import com.stanleymesa.core.ui.theme.LocalDimen
import com.stanleymesa.core.util.extentions.isTrue
import com.stanleymesa.detail_presentation.component.FollowersSection
import com.stanleymesa.detail_presentation.component.RepositoryCard
import com.stanleymesa.detail_presentation.component.UserRow

@OptIn(ExperimentalFoundationApi::class)
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
        topBar = {
            DefaultTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                navHostController = navHostController,
                titleId = R.string.user_profile,
                onBack = { navHostController.navigateUp() }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarState,
                snackbar = { DefaultSnackbar(data = it) },
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(LocalDimen.current.regular)
            ) {
                /** User Section */
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .size(LocalDimen.current.extraLarge)
                                .clip(CircleShape),
                            model = "",
                            placeholder = painterResource(R.drawable.ic_github),
                            contentDescription = "",
                        )
                        DefaultSpacer()
                        Text(
                            text = "Stanley Mesa",
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
                            color = MaterialTheme.colorScheme.primary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        DefaultSpacer(height = LocalDimen.current.small)
                        Text(
                            text = "@stanleymesa",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.secondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        DefaultSpacer(height = LocalDimen.current.small)
                        Text(
                            text = "Joined 2018",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        DefaultSpacer(height = LocalDimen.current.medium)
                        Text(
                            text = "I post awesome Android stuff on my Instagram page @_philipplackner_ and on my YouTube channel Philipp Lackner.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                /** Work Section */
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        DefaultSpacer(height = LocalDimen.current.regular)
                        UserRow(
                            modifier = Modifier.fillMaxWidth(),
                            icon = painterResource(R.drawable.ic_company),
                            text = "Google"
                        )
                        DefaultSpacer(height = LocalDimen.current.default)
                        UserRow(
                            modifier = Modifier.fillMaxWidth(),
                            icon = painterResource(R.drawable.ic_location),
                            text = "Jakarta, Indonesia"
                        )
                        DefaultSpacer(height = LocalDimen.current.default)
                        UserRow(
                            modifier = Modifier.fillMaxWidth(),
                            icon = painterResource(R.drawable.ic_mail),
                            text = "stanleymesa2001@gmail.com"
                        )
                    }
                }
                /** Followers Section */
                item {
                    DefaultSpacer(height = LocalDimen.current.extraRegular)
                    FollowersSection(modifier = Modifier.fillMaxWidth())
                    DefaultSpacer(height = LocalDimen.current.regular)
                }
                /** Repository Header */
                stickyHeader {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(vertical = LocalDimen.current.regular)
                    ) {
                        Text("Repositories", style = MaterialTheme.typography.titleLarge)
                    }
                }
                /** Repositories */
                items(
                    count = 20
                ) {
                    RepositoryCard(
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (it < 19) {
                        DefaultSpacer(height = LocalDimen.current.medium)
                    }
                }

            }

            if (state.isLoading.isTrue()) {
                DefaultProgress(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

package com.stanleymesa.detail_presentation.bri

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.stanleymesa.core.ui.component.compose.DefaultButton
import com.stanleymesa.core.ui.component.compose.DefaultSnackbar
import com.stanleymesa.core.ui.component.compose.DefaultSpacer
import com.stanleymesa.core.ui.component.compose.DefaultTopAppBar
import com.stanleymesa.core.ui.component.compose.showDefaultSnackbar
import com.stanleymesa.core.ui.theme.LocalDimen
import com.stanleymesa.core.ui.theme.LocalShapes
import com.stanleymesa.detail_presentation.bri.component.BriCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BriScreen(
    navHostController: NavHostController,
    state: BriState,
    onEvent: (BriEvent) -> Unit,
) {
    val snackbarState = remember {
        SnackbarHostState()
    }
    val context = LocalContext.current
    val view = LocalView.current
    val itemCount = 20

    LaunchedEffect(state.snackbar) {
        if (!state.snackbar.message.isNullOrBlank() || state.snackbar.messageId != null) {
            onEvent(BriEvent.ResetSnackbar())
            snackbarState.showDefaultSnackbar(
                context = context,
                snackbar = state.snackbar,
                actionLabel = context.getString(com.stanleymesa.core.R.string.ok),
            ).apply {
                when (this) {
                    SnackbarResult.Dismissed -> {}
                    SnackbarResult.ActionPerformed -> {
                        onEvent(BriEvent.ResetSnackbar(false))
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        topBar = {
            // todo center title
            DefaultTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                navHostController = navHostController,
                title = "Daftar Transaksi",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            /** Header */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(LocalDimen.current.regular)
            ) {
                /** Bank Card */
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = 0.2f)),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(LocalDimen.current.regular),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(LocalShapes.medium)
                                    .background(
                                        MaterialTheme.colorScheme.primaryContainer
                                    )
                            )
                            DefaultSpacer(width = LocalDimen.current.medium)
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Bakso Mamat")
                                DefaultSpacer(height = LocalDimen.current.small)
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = buildAnnotatedString {
                                        append("Rp 1.000.000.000")
                                        append(" • ")
                                        append("1200 Transaksi")
                                    },
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = LocalDimen.current.regular,
                                end = LocalDimen.current.default,
                                bottom = LocalDimen.current.default,
                                top = LocalDimen.current.default
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("EDC • 12345678")
                        Box(
                            modifier = Modifier
                                .clip(LocalShapes.large)
                                .clickable {
                                    // Todo: add action here
                                }
                        ) {
                            Row(
                                modifier = Modifier.padding(
                                    horizontal = LocalDimen.current.default,
                                    vertical = LocalDimen.current.small
                                )
                            ) {
                                Text("Ubah outlet")
                                DefaultSpacer(width = LocalDimen.current.default)
                                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
                            }
                        }

                    }
                }
                DefaultSpacer(height = LocalDimen.current.medium)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    /** Segmented Button */
                    SingleChoiceSegmentedButtonRow(modifier = Modifier) {
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = 0, count = 2
                            ),
                            colors = SegmentedButtonDefaults.colors(
                                activeContainerColor = MaterialTheme.colorScheme.primary,
                                activeContentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            icon = { },
                            onClick = {
                            },
                            selected = true,
                        ) {
                            Text("Kartu", style = MaterialTheme.typography.bodyMedium)
                        }
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = 1, count = 2
                            ),
                            colors = SegmentedButtonDefaults.colors(
                                activeContainerColor = MaterialTheme.colorScheme.primary,
                                activeContentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            icon = { },
                            onClick = {
                            },
                            selected = false,
                        ) {
                            Text("QRIS", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                    /** Filter Button */
                    DefaultButton(
                        modifier = Modifier,
                        text = "Filter",
                        suffixIcon = Icons.Default.FilterList,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.onBackground
                        ),
                        border = ButtonDefaults.outlinedButtonBorder()
                    ) { }
                }
                DefaultSpacer(height = LocalDimen.current.default)
            }

            /** Transaction List */
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(
                    start = LocalDimen.current.regular,
                    end = LocalDimen.current.regular,
                    bottom = LocalDimen.current.large
                )
            ) {
                items(itemCount) { index ->
                    BriCard(modifier = Modifier.fillMaxWidth())
                    if (index < itemCount - 1) {
                        DefaultSpacer(height = LocalDimen.current.medium)
                    }
                }
            }
        }
    }
}

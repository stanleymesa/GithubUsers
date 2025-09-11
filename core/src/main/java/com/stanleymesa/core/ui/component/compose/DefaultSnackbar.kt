package com.stanleymesa.core.ui.component.compose

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import com.stanleymesa.core.enums.ScreenSize
import com.stanleymesa.core.ui.theme.LocalDimen
import com.stanleymesa.core.util.SnackbarState
import com.stanleymesa.core.util.extentions.ifNullOrEmpty
import com.stanleymesa.core.util.extentions.orResourceStringEmpty

@Composable
fun DefaultSnackbar(
    modifier: Modifier = Modifier,
    data: SnackbarData,
    screenSize: ScreenSize = ScreenSize.NORMAL
) {
    runCatching {
        val snackbarData = data.visuals as DefaultSnackbarVisuals
        val actionLabel = snackbarData.actionLabel
        val contentColor =
            if (snackbarData.isSuccess) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onErrorContainer
        val containerColor =
            if (snackbarData.isSuccess) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer
        val actionComposable: (@Composable () -> Unit)? = if (actionLabel != null) {
            @Composable {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(contentColor = contentColor),
                    onClick = { data.performAction() },
                    content = {
                        Text(
                            text = actionLabel,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = contentColor
                            )
                        )
                    }
                )
            }
        } else {
            null
        }
        val dismissActionComposable: (@Composable () -> Unit)? =
            if (snackbarData.withDismissAction) {
                @Composable {
                    IconButton(
                        onClick = { data.dismiss() },
                        content = {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = "Close",
                                tint = contentColor
                            )
                        }
                    )
                }
            } else {
                null
            }
        Box(
            modifier = modifier.padding(
                horizontal = if (screenSize == ScreenSize.NORMAL) LocalDimen.current.regular else LocalDimen.current.extraLarge,
                vertical = LocalDimen.current.regular
            )
        ) {
            Snackbar(
                modifier = Modifier
                    .shadow(
                        elevation = LocalDimen.current.extraSmall,
                        shape = RoundedCornerShape(LocalDimen.current.default),
                    ),
                contentColor = contentColor,
                containerColor = containerColor,
                actionContentColor = contentColor,
                action = actionComposable,
                dismissActionContentColor = contentColor,
                dismissAction = dismissActionComposable,
                shape = RoundedCornerShape(LocalDimen.current.default),
            ) {
                Row {
                    Text(
                        text = snackbarData.message,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = contentColor
                        )
                    )
                }
            }
        }
    }
}

data class DefaultSnackbarVisuals(
    override val message: String,
    val isSuccess: Boolean,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false
) : SnackbarVisuals

suspend fun SnackbarHostState.showDefaultSnackbar(
    context: Context,
    snackbar: SnackbarState,
    actionLabel: String? = null,
    withDismissAction: Boolean = false,
    duration: SnackbarDuration = SnackbarDuration.Short,
): SnackbarResult {
    return this.showSnackbar(
        DefaultSnackbarVisuals(
            message = snackbar.message.ifNullOrEmpty { context.getString(snackbar.messageId.orResourceStringEmpty()) },
            isSuccess = snackbar.isSuccess,
            actionLabel = actionLabel,
            withDismissAction = withDismissAction,
            duration = duration
        ),
    )
}
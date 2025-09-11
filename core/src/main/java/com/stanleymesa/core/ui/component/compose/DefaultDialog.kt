package com.stanleymesa.core.ui.component.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.stanleymesa.core.R
import com.stanleymesa.core.ui.theme.LocalDimen


@Composable
fun DefaultDialog(
    title: String,
    desc: String = "",
    annotatedDesc: AnnotatedString? = null,
    icon: ImageVector? = null,
    positiveButtonText: String = "",
    negativeButtonText: String = "",
    positiveContainerColor: Color = MaterialTheme.colorScheme.primary,
    positiveOnContainerColor: Color = MaterialTheme.colorScheme.onPrimary,
    positiveButtonShow: Boolean = true,
    negativeButtonShow: Boolean = false,
    positiveButtonEnabled: Boolean = true,
    negativeButtonEnabled: Boolean = true,
    canDismissOutside: Boolean = true,
    onDismissRequest: () -> Unit,
    onPositive: () -> Unit,
    onNegative: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = { onDismissRequest.invoke() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = canDismissOutside,
            dismissOnClickOutside = canDismissOutside
        ),
    ) {
        Card(
            modifier = Modifier.padding(LocalDimen.current.regular),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = LocalDimen.current.maxDialogSize)
                    .fillMaxWidth()
                    .padding(LocalDimen.current.regular),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (icon != null) {
                    Icon(
                        icon,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(LocalDimen.current.large),
                        contentDescription = stringResource(id = R.string.icon)
                    )
                    DefaultSpacer()
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onBackground)
                )
                DefaultSpacer(height = LocalDimen.current.medium)
                if (annotatedDesc != null) {
                    Text(
                        text = annotatedDesc,
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        text = desc,
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
                        textAlign = TextAlign.Center
                    )
                }
                DefaultSpacer(height = LocalDimen.current.regular)
                Column(modifier = Modifier.fillMaxWidth()) {
                    if (positiveButtonShow) {
                        DefaultButton(
                            modifier = Modifier.fillMaxWidth(),
                            text = positiveButtonText,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = positiveContainerColor,
                                contentColor = positiveOnContainerColor
                            ),
                            enabled = positiveButtonEnabled
                        ) {
                            onPositive.invoke()
                            onDismissRequest.invoke()
                        }
                    }
                    if (negativeButtonShow && positiveButtonShow) {
                        DefaultSpacer(height = LocalDimen.current.small)
                    }
                    if (negativeButtonShow) {
                        DefaultTextButton(
                            modifier = Modifier.fillMaxWidth(),
                            text = negativeButtonText,
                            enabled = negativeButtonEnabled
                        ) {
                            onNegative.invoke()
                            onDismissRequest.invoke()
                        }
                    }
                }

            }
        }
    }
}
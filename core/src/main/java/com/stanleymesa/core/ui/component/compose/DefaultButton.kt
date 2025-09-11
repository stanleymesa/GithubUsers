package com.stanleymesa.core.ui.component.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.stanleymesa.core.enums.ButtonSize
import com.stanleymesa.core.ui.theme.LocalDimen

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    buttonSize: ButtonSize = ButtonSize.MEDIUM,
    text: String,
    prefixIcon: ImageVector? = null,
    contentHorizontalPadding: Dp = if (prefixIcon == null) LocalDimen.current.extraRegular else LocalDimen.current.regular,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        contentPadding = PaddingValues(
            horizontal = contentHorizontalPadding,
            vertical = when (buttonSize) {
                ButtonSize.SMALL -> LocalDimen.current.default
                ButtonSize.MEDIUM -> LocalDimen.current.medium
                ButtonSize.LARGE -> LocalDimen.current.regular
            },
        ),
        colors = colors,
        enabled = enabled,
        onClick = onClick
    ) {
        if (prefixIcon != null) {
            Icon(
                imageVector = prefixIcon,
                contentDescription = "",
                modifier = Modifier.size(
                    when (buttonSize) {
                        ButtonSize.SMALL -> LocalDimen.current.regular
                        ButtonSize.MEDIUM -> 20.dp
                        ButtonSize.LARGE -> LocalDimen.current.extraRegular
                    }
                ),
                tint = MaterialTheme.colorScheme.onPrimary
            )
            DefaultSpacer(width = LocalDimen.current.default)
        }
        Text(
            text = text, style = when (buttonSize) {
                ButtonSize.SMALL -> MaterialTheme.typography.bodySmall
                ButtonSize.MEDIUM -> MaterialTheme.typography.labelMedium
                ButtonSize.LARGE -> MaterialTheme.typography.labelLarge
            }
        )
        if (prefixIcon != null) {
            DefaultSpacer(width = LocalDimen.current.default)
        }
    }
}
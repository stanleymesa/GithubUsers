package com.stanleymesa.core.ui.component.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import com.stanleymesa.core.ui.theme.LocalDimen

@Composable
fun DefaultEmptyState(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    icon: ImageVector? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(LocalDimen.current.regular),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon?.let {
            Icon(
                modifier = Modifier.size(LocalDimen.current.extraLarge),
                imageVector = it,
                contentDescription = title
            )
        }
        DefaultSpacer(height = LocalDimen.current.regular)
        Text(
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge
        )
        DefaultSpacer(height = LocalDimen.current.small)
        Text(
            text = message,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
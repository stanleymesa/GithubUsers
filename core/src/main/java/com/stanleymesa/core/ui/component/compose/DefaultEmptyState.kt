package com.stanleymesa.core.ui.component.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.stanleymesa.core.ui.theme.LocalDimen

@Composable
fun DefaultEmptyState(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    icon: Painter? = null,
    spaceFraction: Int? = null
) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(LocalDimen.current.regular),
        verticalArrangement = if (spaceFraction != null && spaceFraction > 0) Arrangement.Top else Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (spaceFraction != null && spaceFraction > 0) {
            DefaultSpacer(height = screenHeightDp / spaceFraction)
        }
        icon?.let {
            Image(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(horizontal = LocalDimen.current.extraLarge)
                    .width(screenWidthDp / 2),
                painter = it,
                contentDescription = title
            )
            DefaultSpacer(height = LocalDimen.current.medium)
        }
        DefaultSpacer(height = LocalDimen.current.extraRegular)
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
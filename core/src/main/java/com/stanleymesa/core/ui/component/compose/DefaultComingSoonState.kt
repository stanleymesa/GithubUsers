package com.stanleymesa.core.ui.component.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.stanleymesa.core.R
import com.stanleymesa.core.ui.theme.LocalDimen

@Composable
fun DefaultComingSoonState(modifier: Modifier = Modifier, title: String) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            modifier = Modifier.width(screenWidthDp / 2),
            painter = painterResource(id = R.drawable.img_nodata),
            contentDescription = ""
        )
        DefaultSpacer(height = LocalDimen.current.extraRegular)
        Text(text = title, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
    }
}
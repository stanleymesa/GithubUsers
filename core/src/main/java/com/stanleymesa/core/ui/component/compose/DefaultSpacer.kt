package com.stanleymesa.core.ui.component.compose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.stanleymesa.core.ui.theme.LocalDimen

@Composable
fun DefaultSpacer(
    height: Dp = LocalDimen.current.regular,
    width: Dp = LocalDimen.current.regular,
) {
    Spacer(
        modifier = Modifier
            .height(height)
            .width(width)
    )
}
package com.stanleymesa.core.ui.component.compose

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxWithSwipeRefresh(
    modifier: Modifier = Modifier,
    onSwipe: () -> Unit,
    isRefreshing: Boolean,
    content: @Composable BoxScope.() -> Unit
) {
    PullToRefreshBox(modifier = Modifier, isRefreshing = isRefreshing, onRefresh = { onSwipe() }) {
        content()
    }
}
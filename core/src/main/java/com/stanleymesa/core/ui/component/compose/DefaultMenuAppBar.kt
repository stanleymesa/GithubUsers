package com.stanleymesa.core.ui.component.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.stanleymesa.core.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultMenuAppBar(
    modifier: Modifier = Modifier,
    @StringRes titleId: Int = R.string.empty_string,
    title: String = "",
    actions: @Composable RowScope.() -> Unit = {},
    onMenuClicked: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = title.ifEmpty { stringResource(id = titleId) }) },
        navigationIcon = {
            IconButton(onClick = onMenuClicked) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = "Menu"
                )
            }
        },
        actions = actions
    )
}
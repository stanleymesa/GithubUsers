package com.stanleymesa.core.ui.component.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.stanleymesa.core.R
import com.stanleymesa.core.util.extentions.isTrue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopAppBar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    @StringRes titleId: Int = R.string.empty_string,
    title: String = "",
    actions: @Composable RowScope.() -> Unit = {},
    canBack: Boolean = true,
    onBack: (() -> Unit)? = null
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = title.ifEmpty { stringResource(id = titleId) }) },
        navigationIcon = {
            if (canBack.isTrue()) {
                IconButton(
                    onClick = {
                        if (onBack != null) {
                            onBack.invoke()
                        } else {
                            navHostController.navigateUp()
                        }
                    },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = actions
    )
}
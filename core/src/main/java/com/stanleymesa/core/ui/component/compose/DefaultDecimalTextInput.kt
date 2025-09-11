package com.stanleymesa.core.ui.component.compose

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.stanleymesa.core.ui.theme.LocalDimen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultDecimalTextInput(
    value: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    onValueChange: (String) -> Unit = {},
    suffix: String? = null
) {
    BasicTextField(
        value = value,
        modifier = modifier,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
        maxLines = 1,
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        ),
        decorationBox = { i ->
            TextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = i,
                enabled = isEnabled,
                singleLine = true,
                suffix = {
                    if (suffix != null) Text(
                        text = suffix,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                visualTransformation = VisualTransformation.None,
                interactionSource = remember { MutableInteractionSource() },
                placeholder = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = placeholder,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                contentPadding = PaddingValues(
                    horizontal = LocalDimen.current.regular,
                    vertical = LocalDimen.current.default
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledTextColor = MaterialTheme.colorScheme.onSurface
                ),
            )
        }
    )
}
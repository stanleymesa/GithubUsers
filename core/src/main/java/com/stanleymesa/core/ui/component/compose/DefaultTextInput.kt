package com.stanleymesa.core.ui.component.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.stanleymesa.core.R
import com.stanleymesa.core.ui.theme.LocalDimen

@Composable
fun DefaultTextInput(
    value: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    inputType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    isEnabled: Boolean = true,
    isMultiline: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.Words,
    maxCounter: Int = 0,
    errorText: String = "",
    prefixText: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    readOnly: Boolean = false,
    autoFocus: Boolean = false,
    onDone: () -> Unit = {},
    onValueChange: (String) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember {
        FocusRequester()
    }
    val isTransformText = remember {
        mutableStateOf(inputType == KeyboardType.Password)
    }

    if (autoFocus) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }

    OutlinedTextField(
        value = value,
        enabled = isEnabled,
        readOnly = readOnly,
        textStyle = textStyle,
        onValueChange = {
            if (maxCounter != 0) {
                if (it.length <= maxCounter) onValueChange.invoke(it)
            } else {
                onValueChange.invoke(it)
            }
        },
        modifier = modifier.focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            capitalization = if (isMultiline) KeyboardCapitalization.Sentences else keyboardCapitalization,
            keyboardType = inputType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                onDone.invoke()
            }
        ),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon ?: if (inputType == KeyboardType.Password) {
            {
                IconButton(
                    onClick = {
                        isTransformText.value = !isTransformText.value
                    }
                ) {
                    Icon(
                        painter = painterResource(id = if (isTransformText.value) R.drawable.ic_eye_on else R.drawable.ic_eye_off),
                        contentDescription = ""
                    )
                }
            }
        } else null,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledTextColor = MaterialTheme.colorScheme.onSurface
        ),
        visualTransformation = if (isTransformText.value) PasswordVisualTransformation() else VisualTransformation.None,
        shape = RoundedCornerShape(LocalDimen.current.regular),
        placeholder = {
            Text(text = placeholder)
        },
        supportingText = if (maxCounter != 0) {
            {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "${value.length} / $maxCounter",
                    textAlign = TextAlign.End
                )
            }
        } else {
            if (errorText.isNotBlank()) {
                {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = errorText,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            } else null
        },
        maxLines = if (isMultiline) 4 else 1,
        singleLine = !isMultiline,
        prefix = prefixText,
    )
}
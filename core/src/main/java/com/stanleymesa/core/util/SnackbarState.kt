package com.stanleymesa.core.util

data class SnackbarState(
    val message: String? = null,
    val messageId: Int? = null,
    val isSuccess: Boolean = true
)

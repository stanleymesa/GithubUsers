package com.stanleymesa.detail_presentation

import com.stanleymesa.core.shared_data.model.User
import com.stanleymesa.core.util.SnackbarState
import com.stanleymesa.detail_domain.model.UserRepos

data class DetailState(
    val snackbar: SnackbarState = SnackbarState(),
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,

    val user: User? = null,
    val userRepos: List<UserRepos> = emptyList(),
)
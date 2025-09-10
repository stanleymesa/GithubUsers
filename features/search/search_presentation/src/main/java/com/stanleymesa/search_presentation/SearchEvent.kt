package com.stanleymesa.search_presentation

import com.stanleymesa.core.util.SnackbarState


sealed class SearchEvent {
    data class SetSnackbar(val snackbarState: SnackbarState) : SearchEvent()
    data class ResetSnackbar(val isDelay: Boolean = true) : SearchEvent()
    data class SetLoading(val isLoading: Boolean) : SearchEvent()
    data class SetLinearLoading(val isLoading: Boolean) : SearchEvent()
    data class SetRefreshing(val isRefreshing: Boolean) : SearchEvent()

    data class OnSearchChange(val search: String) : SearchEvent()
}
package com.stanleymesa.githubusers.route

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.stanleymesa.core.route.SearchRoute
import com.stanleymesa.search_presentation.SearchScreen
import com.stanleymesa.search_presentation.SearchViewModel

fun NavGraphBuilder.searchRoute(
    navHostController: NavHostController,
) = composable<SearchRoute> { _ ->
    val viewModel: SearchViewModel = hiltViewModel()

    SearchScreen(
        navHostController = navHostController,
        state = viewModel.state.collectAsState().value,
        onEvent = viewModel::onEvent,
        lazyPagingItems = viewModel.users.collectAsLazyPagingItems()
    )
}
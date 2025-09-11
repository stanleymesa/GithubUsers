package com.stanleymesa.githubusers.route

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.stanleymesa.core.route.DetailRoute
import com.stanleymesa.detail_presentation.DetailScreen
import com.stanleymesa.detail_presentation.DetailViewModel

fun NavGraphBuilder.detailRoute(
    navHostController: NavHostController,
) = composable<DetailRoute> { _ ->
    val viewModel: DetailViewModel = hiltViewModel()

    DetailScreen(
        navHostController = navHostController,
        state = viewModel.state.collectAsState().value,
        onEvent = viewModel::onEvent,
    )
}
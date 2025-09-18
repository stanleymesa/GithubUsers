package com.stanleymesa.githubusers.route

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.stanleymesa.core.route.BriRoute
import com.stanleymesa.detail_presentation.bri.BriScreen
import com.stanleymesa.detail_presentation.bri.BriViewModel

fun NavGraphBuilder.briRoute(
    navHostController: NavHostController,
) = composable<BriRoute> { _ ->
    val viewModel: BriViewModel = hiltViewModel()

    BriScreen(
        navHostController = navHostController,
        state = viewModel.state.collectAsState().value,
        onEvent = viewModel::onEvent,
    )
}
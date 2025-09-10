package com.stanleymesa.githubusers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.stanleymesa.core.enums.Themes
import com.stanleymesa.core.route.SearchRoute
import com.stanleymesa.core.ui.theme.GithubTheme
import com.stanleymesa.githubusers.route.detailRoute
import com.stanleymesa.githubusers.route.searchRoute
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
//            installSplashScreen().apply {
//                setKeepOnScreenCondition {
//                    !isSplashDone
//                }
//            }
//            delay(2000)
//            isSplashDone = true

            enableEdgeToEdge()

            setContent {
                val themeState = viewModel.themesSetting.collectAsState().value
                val isDarkMode = themeState == Themes.DARK || themeState == Themes.DYNAMIC_DARK
                val isDynamic =
                    themeState == Themes.DYNAMIC_LIGHT || themeState == Themes.DYNAMIC_DARK

                GithubTheme(
                    darkTheme = isDarkMode,
                    dynamicColor = isDynamic,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surface),
                    ) {
                        val navController = rememberNavController()

                        NavHost(
                            navController = navController,
                            startDestination = SearchRoute,
                            enterTransition = { fadeIn(animationSpec = tween(250)) },
                            exitTransition = { fadeOut(animationSpec = tween(250)) },
                            popEnterTransition = { fadeIn(animationSpec = tween(250)) },
                            popExitTransition = { fadeOut(animationSpec = tween(250)) },
                        ) {
                            searchRoute(navController)
                            detailRoute(navController)
                        }

                    }
                }
            }
        }
    }
}

package com.stanleymesa.core.util

sealed class Screen(val route: String) {
    object Main : Screen("main_screen")
    object Setting : Screen("setting_screen")
    object Oil : Screen("oil_screen")
    object OilSelection : Screen("oil_selection_screen")
    object Recipe : Screen("recipe_screen")
    object Summary : Screen("summary_screen")
    object Create : Screen("create_screen")
    object OilDetail : Screen("oil_detail_screen")
    object Home : Screen("home_screen")
    object Login : Screen("login_screen")
    object Register : Screen("register_screen")
    object Explore : Screen("explore_screen")
    object Articles : Screen("articles_screen")
    object ChangePassword : Screen("change_password_screen")
    object ForgotPassword : Screen("forgot_password_screen")
    object Detail : Screen("detail_screen")
}
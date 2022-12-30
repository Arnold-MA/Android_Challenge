package com.tech.androidchallenge.navigation

sealed class AppScreens(
    val route: String,
) {
    object SplashScreen: AppScreens("splash_screen")
    object LoginScreen: AppScreens("login_screen")
    object SignUpScreen: AppScreens("signup_screen")
    object MainScreen: AppScreens("main_screen")
}
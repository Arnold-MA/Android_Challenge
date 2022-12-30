package com.tech.androidchallenge.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tech.androidchallenge.Login
import com.tech.androidchallenge.MySplashScreen
import com.tech.androidchallenge.MainScreen
import com.tech.androidchallenge.SignUp

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.SplashScreen.route
    ) {
        composable(AppScreens.SplashScreen.route) {
            MySplashScreen(navController)
        }
        composable(AppScreens.LoginScreen.route) {
            Login(navController)
        }
        composable(AppScreens.MainScreen.route) {
            MainScreen(navController)
        }
        composable(AppScreens.SignUpScreen.route) {
            SignUp(navController)
        }
    }
}
package com.tech.androidchallenge.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tech.androidchallenge.navigation.MenuScreens.*
import com.tech.androidchallenge.screens.*

@Composable
fun MenuNavigation(
    navController: NavHostController,
    setTitle: (String) -> Unit,
) {

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = "route_home",
                modifier = Modifier.fillMaxSize()
            ) {
                composable(Home.route) {
                    setTitle(Home.title)
                    Text(text = "Bienvenido")
                }
                composable(ProductsList.route) {
                    setTitle(ProductsList.title)
                    ProductListScreen(navController)
                }
                composable(ProductAdd.route) {
                    setTitle(ProductAdd.title)
                    AddProductScreen(navController)
                }
                composable("${ProductModify.route}/{productId}",
                    arguments = listOf(navArgument("productId"){type = NavType.StringType })) { backStackEntry ->
                    setTitle("${ProductModify.title}: ${backStackEntry.arguments?.getString("productId")}")
                    ModifyProductScreen(navController, backStackEntry.arguments?.getString("productId"))
                }
            }
        }
    }

}
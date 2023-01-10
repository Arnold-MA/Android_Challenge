package com.tech.androidchallenge.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Send
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MenuScreens (
    val route: String,
    val icon: ImageVector,
    val title: String,
    val nav: Int
){
    object Home: MenuScreens("route_home", Icons.Filled.Home, "Android Challenge", 0)
    object ProductsList: MenuScreens("route_list", Icons.Filled.List, "Lista de productos", 1)
    object ProductAdd: MenuScreens("route_add", Icons.Default.Add, "Agregar producto", 2)
    object ProductModify: MenuScreens("route_modify", Icons.Default.Send, "Modificar producto", 3)
}
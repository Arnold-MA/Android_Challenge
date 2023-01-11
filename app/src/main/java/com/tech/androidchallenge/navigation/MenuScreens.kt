package com.tech.androidchallenge.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MenuScreens (
    val route: String,
    val icon: ImageVector,
    val title: String,
){
    object Home: MenuScreens("route_home", Icons.Filled.Home, "Android Challenge")
    object ProductsList: MenuScreens("route_list", Icons.Filled.List, "Lista de productos")
    object ProductAdd: MenuScreens("route_add", Icons.Default.Add, "Agregar producto")
    object ProductModify: MenuScreens("route_modify", Icons.Default.Send, "Modificar producto")
    object PostsList: MenuScreens("route_post_list", Icons.Default.Share, "Lista de publicaciones")
}
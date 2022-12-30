package com.tech.androidchallenge.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MenuNavigation (
    val route: String,
    val icon: ImageVector,
    val title: String,
    val nav: Int
){
    object UserList: MenuNavigation("user_list", Icons.Filled.List, "Lista de usuarios", 1)
    object UserAdd: MenuNavigation("user_add", Icons.Default.Add, "Agregar usuario", 2)
    object Logout: MenuNavigation("logout", Icons.Default.ExitToApp, "Cerrar sesión", 3)
}
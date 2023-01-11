package com.tech.androidchallenge

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tech.androidchallenge.database.ProductEntity
import com.tech.androidchallenge.datastore.DataPreferences
import com.tech.androidchallenge.navigation.AppScreens
import com.tech.androidchallenge.navigation.MenuNavigation
import com.tech.androidchallenge.navigation.MenuScreens
import com.tech.androidchallenge.navigation.MenuScreens.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(navController: NavHostController) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var title by rememberSaveable{ mutableStateOf("Android Challenge") }
    var mainNavController = rememberNavController()
    val menuItems = listOf(
        Home,
        ProductsList,
        ProductAdd,
        PostsList
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {TopBar(
            scope = scope,
            scaffoldState = scaffoldState,
            title = title
        )},
        drawerContent = { Drawer(
            menu_items = menuItems,
            scope,
            scaffoldState,
            navController,
            mainNavController
        )},

    ) {
        MenuNavigation(
            navController = mainNavController,
            setTitle = {title = it}
        )
    }
}

@Composable
fun Drawer(
    menu_items: List<MenuScreens>,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    mainNavController: NavHostController
) {
    val context = LocalContext.current
    val dataStore = DataPreferences(context = context)
    val name = remember{ mutableStateOf("") }
    val lastname = remember{ mutableStateOf("") }
    name.value = dataStore.getName.collectAsState(initial = "").value
    lastname.value = dataStore.getLastname.collectAsState(initial = "").value


    Column() {
        Surface(
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.fillMaxWidth(),

        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(
                    text = "${name.value} ${lastname.value}",
                    style = MaterialTheme.typography.h4,
                )
            }
        }
        menu_items.forEach{ item ->
            DrawerItem(
                item = item,
                scope,
                scaffoldState,
                onItemClick = { route ->

                },
                mainNavController
            )
        }
        LogoutButton(navController = navController, scope = scope)
    }
}

@Composable
fun DrawerItem(
    item: MenuScreens,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    onItemClick: (route: String) -> Unit,
    mainNavController: NavHostController
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(bounded = true, color = Color.Red),
                    onClick = {
                        onItemClick(item.route)
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                        mainNavController.navigate(route = item.route)
                    }
                ).padding(all = 8.dp)
        ){
            Row() {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
fun LogoutButton(
    navController: NavHostController,
    scope: CoroutineScope,
) {
    val dataStore = DataPreferences(context = LocalContext.current)

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(bounded = true, color = Color.Red),
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(AppScreens.LoginScreen.route)
                        scope.launch {
                            dataStore.deleteSaving()
                        }
                    }
                ).padding(all = 8.dp)
        ){
            Row() {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "logout",
                    tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Cerrar sesión",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }


}

@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    title: String
)
{

    TopAppBar(
        title = { Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = title, textAlign = TextAlign.Center)
        } },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Hamburger menu"
                )
            }
        }
    )
}



@Preview(showBackground = true)
@Composable
fun MyMenuPreview() {
    val navController = rememberNavController()
    MainScreen(navController)
}
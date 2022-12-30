package com.tech.androidchallenge

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tech.androidchallenge.datastore.DataPreferences
import com.tech.androidchallenge.navigation.AppScreens
import com.tech.androidchallenge.navigation.MenuNavigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(navController: NavHostController) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var view by remember{ mutableStateOf(1) }
    val menuItems = listOf(
        MenuNavigation.UserList,
        MenuNavigation.UserAdd,
        MenuNavigation.Logout
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {TopBar(scope, scaffoldState)},
        drawerContent = { Drawer(
            menu_items = menuItems,
            setScreen = {view = it},
            scope,
            scaffoldState,
            navController
        )},
    ) {
        if (view == 1) {
            UserList()
        }
        else if (view == 2) {
            UserAdd()
        }
        else {

        }
    }
}

@Composable
fun Drawer(
    menu_items: List<MenuNavigation>,
    setScreen: (Int) -> Unit,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController
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
                modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)
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
                setScreen = setScreen,
                scope,
                scaffoldState,
                navController
            )
        }
    }
}

@Composable
fun DrawerItem(
    item: MenuNavigation,
    setScreen: (Int) -> Unit,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController
) {
    val dataStore = DataPreferences(context = LocalContext.current)

    Row(
        modifier = Modifier
            .fillMaxWidth()

        //.padding(8.dp)
    ) {

        TextButton(onClick = {
            if (item.nav == 3){
                navController.popBackStack()
                navController.navigate(AppScreens.LoginScreen.route)
                scope.launch {
                    dataStore.deleteSaving()
                }
            }
            else {
                setScreen(item.nav)
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }
        })
        {
            Icon(imageVector = item.icon, contentDescription = item.title)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.body1
            )
        }
    }

}

@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
)
{

    TopAppBar(
        title = { Text(text = "Android Challenge" )},
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
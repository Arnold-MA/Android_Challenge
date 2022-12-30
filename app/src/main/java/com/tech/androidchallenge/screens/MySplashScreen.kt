package com.tech.androidchallenge

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tech.androidchallenge.datastore.DataPreferences
import com.tech.androidchallenge.navigation.AppScreens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MySplashScreen(navController: NavHostController) {
    val context = LocalContext.current
    val dataStore = DataPreferences(context = context)
    val username = remember{ mutableStateOf("") }
    username.value = dataStore.getUsername.collectAsState(initial = "").value
    LaunchedEffect(key1 = true) {
        delay(2000)
        navController.popBackStack()
        if (username.value == "") {
            navController.navigate(AppScreens.LoginScreen.route)
        }
        else {
            navController.navigate(AppScreens.MainScreen.route)
        }
    }
    Splash()
}

@Composable
fun Splash() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_techera_logo),
            contentDescription = "Logo TechEra",
            modifier = Modifier.fillMaxWidth(0.85f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MySplashScreenPreview(){
    val navController = rememberNavController()
    MySplashScreen(navController)
}
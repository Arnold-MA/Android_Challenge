package com.tech.androidchallenge

import android.content.Context
import android.view.textclassifier.TextLinks.TextLinkSpan
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.buildSpannedString
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.tech.androidchallenge.database.MyDatabase
import com.tech.androidchallenge.database.UserEntity
import com.tech.androidchallenge.datastore.DataPreferences
import com.tech.androidchallenge.navigation.AppScreens
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun Login(navController: NavHostController) {

    val context: Context = LocalContext.current
    val database = Room.databaseBuilder(
        context,
        MyDatabase::class.java,
        "android_database")
        .build()
    val userDao = database.userDao()
    var username by remember{ mutableStateOf<String>("")}
    var password by remember{ mutableStateOf<String>("")}

    val scope = rememberCoroutineScope()
    val dataStore = DataPreferences(context)
    val usernameDemo = dataStore.getUsername.collectAsState(initial = "").value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Inicio de Sesión",
            style = MaterialTheme.typography.h3
        )
        IconTextField(
            name = "Usuario",
            placeholder = "Ingrese su nombre de usuario.",
            icon = Icons.Default.Person,
            keyboardType = KeyboardType.Text,
            getText = {username = it}
        )
        PasswordTextField(getPassword = {password = it})
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (username.isNullOrEmpty() || password.isNullOrEmpty()){
                    showAlert(context = context, i = 1)
                }
                else {
                    //val userResult: MutableStateFlow<UserEntity?> = MutableStateFlow(null)
                    scope.launch {
                        val userResult = userDao.getUserByUsernameAndPassword(username = username, password = password)
                        if (userResult != null){
                            dataStore.saveUser(userResult)
                            showAlert(context, 4)
                            navController.popBackStack()
                            navController.navigate(AppScreens.MainScreen.route)
                        }
                        else {
                            showAlert(context, 6)
                        }
                    }

                    /*scope.launch {
                        val userResult = userDao.getUserByUsernameAndPassword(username = username, password = password)
                        if (userResult != null){
                            //scope.launch {
                                dataStore.saveUser(userResult)
                            //}
                            navController.popBackStack()
                            navController.navigate(AppScreens.MainScreen.route)
                        }
                        else {
                            showAlert(context, 6)
                        }
                    }*/

                }
            },
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
            ),
            //border = BorderStroke(4.dp, MaterialTheme.colors.primary),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White,
                backgroundColor = MaterialTheme.colors.primary
            )
        ) {
            Text(
                text = "Iniciar sesión"
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                navController.navigate(AppScreens.SignUpScreen.route)
            },
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
            ),
            //border = BorderStroke(4.dp, MaterialTheme.colors.primary),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White,
                backgroundColor = MaterialTheme.colors.primaryVariant
            )
        ) {
            Text(
                text = "Registrarse"
            )
        }
    }
}


fun showAlert(context: Context, i: Int){
    if (i == 1) {
        Toast.makeText(context, "Debe de llenar todos los campos.", Toast.LENGTH_SHORT).show()
        return
    }
    if (i == 2) {
        Toast.makeText(context, "No se ha podido registrar los datos.", Toast.LENGTH_SHORT).show()
        return
    }
    if (i == 3) {
        Toast.makeText(context, "Usuario registrado previamente.", Toast.LENGTH_SHORT).show()
        return
    }
    if (i == 4) {
        Toast.makeText(context, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show()
        return
    }
    if (i == 5) {
        Toast.makeText(context, "Registro exitoso, se inició la sesión.", Toast.LENGTH_SHORT).show()
        return
    }
    if (i == 6) {
        Toast.makeText(context, "No se ha podido iniciar sesión. Verifique los datos ingresados.", Toast.LENGTH_SHORT).show()
        return
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    val navController = rememberNavController()
    Login(navController)
}
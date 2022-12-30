package com.tech.androidchallenge

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.tech.androidchallenge.database.MyDatabase
import com.tech.androidchallenge.database.UserEntity
import com.tech.androidchallenge.datastore.DataPreferences
import com.tech.androidchallenge.navigation.AppScreens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun SignUp(navController: NavHostController) {
    val context: Context = LocalContext.current
    var username by remember{ mutableStateOf<String>("") }
    var password by remember{ mutableStateOf<String>("") }
    var name by remember{ mutableStateOf<String>("") }
    var lastname by remember{ mutableStateOf<String>("") }

    val database = Room.databaseBuilder(
        context,
        MyDatabase::class.java,
        "android_database")
        .build()
    val userDao = database.userDao()
    val scope = rememberCoroutineScope()
    val dataStore = DataPreferences(context)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Registro",
            style = MaterialTheme.typography.h3
        )
        IconTextField(
            name = "Nombre de usuario",
            placeholder = "Ingrese su nombre de usuario.",
            icon = Icons.Default.Person,
            keyboardType = KeyboardType.Text,
            getText = {username = it}
        )
        IconTextField(
            name = "Nombres",
            placeholder = "Ingrese su nombre(s).",
            icon = Icons.Default.Face,
            keyboardType = KeyboardType.Text,
            getText = {name = it}
        )
        IconTextField(
            name = "Apellidos",
            placeholder = "Ingrese su apellido(s).",
            icon = Icons.Default.Face,
            keyboardType = KeyboardType.Text,
            getText = {lastname = it}
        )
        PasswordTextField(getPassword = {password = it})
        Button(
            onClick = {
                if (username.isNullOrEmpty() || password.isNullOrEmpty()
                    || name.isNullOrEmpty() || lastname.isNullOrEmpty()){
                    showAlert(context = context, i = 1)
                }
                else {
                    val user = UserEntity(username, password, name, lastname)
                    scope.launch {
                        val userDuplicated = userDao.verifyUsername(username)
                        if(userDuplicated == null)
                        {
                            userDao.insert(user)
                            dataStore.saveUser(user)
                            showAlert(context, 5)
                            navController.popBackStack()
                            navController.popBackStack()
                            navController.navigate(AppScreens.MainScreen.route)
                        }
                        else {
                            showAlert(context, 3)
                        }
                    }

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
                text = "Registrarme"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    val navController = rememberNavController()
    SignUp(navController)
}
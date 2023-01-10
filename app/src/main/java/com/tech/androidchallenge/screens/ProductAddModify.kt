package com.tech.androidchallenge.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.twotone.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.tech.androidchallenge.IconTextField
import com.tech.androidchallenge.Login
import com.tech.androidchallenge.PasswordTextField
import com.tech.androidchallenge.database.MyDatabase
import com.tech.androidchallenge.database.ProductEntity
import com.tech.androidchallenge.database.UserEntity
import com.tech.androidchallenge.datastore.DataPreferences
import com.tech.androidchallenge.navigation.AppScreens
import com.tech.androidchallenge.navigation.MenuScreens
import com.tech.androidchallenge.showAlert
import kotlinx.coroutines.launch

@Composable
fun ProductAddModifyScreen() {

    val currentOrientation = LocalConfiguration.current.orientation

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            Text(text = "Lista de productos - Landscape")
        }
        else {
            Text(text = "Lista de productos - Portrait")
        }
    }
}

@Composable
fun AddProductScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val dataStore = DataPreferences(context = context)
    var productId by remember{ mutableStateOf("") }
    var name by remember{ mutableStateOf("") }
    var description by remember{ mutableStateOf("") }
    var price by remember{ mutableStateOf("") }
    var userId by remember{ mutableStateOf("") }
    userId = dataStore.getUsername.collectAsState(initial = "").value

    val database = Room.databaseBuilder(
        context,
        MyDatabase::class.java,
        "android_database")
        .build()
    val productDao = database.productDao()
    val scope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        /*Text(
            text = "Registro",
            style = MaterialTheme.typography.h3
        )*/
        IconTextField(
            name = "*Código del producto",
            placeholder = "Ingrese el código del producto.",
            icon = Icons.Default.KeyboardArrowRight,
            keyboardType = KeyboardType.Text,
            getText = {productId = it}
        )
        IconTextField(
            name = "*Nombre del producto",
            placeholder = "Ingrese el nombre del producto.",
            icon = Icons.Default.KeyboardArrowRight,
            keyboardType = KeyboardType.Text,
            getText = {name = it}
        )
        IconTextField(
            name = "Descripción",
            placeholder = "Ingrese una descripción para el producto.",
            icon = Icons.Default.KeyboardArrowRight,
            keyboardType = KeyboardType.Text,
            getText = {description = it},
            isDescription = true
        )
        IconTextField(
            name = "*Precio",
            placeholder = "Ingrese el precio del producto.",
            icon = Icons.Default.ShoppingCart,
            keyboardType = KeyboardType.Number,
            getText = {price = it}
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                if (productId.isNullOrEmpty() || name.isNullOrEmpty()
                    || price.isNullOrEmpty()){
                    Toast.makeText(context, "Tiene algún campo(s) obligatorio sin llenar.", Toast.LENGTH_SHORT).show()
                }
                else {
                    val product = ProductEntity(productId, name, description, price.toDouble(), true, userId)
                    scope.launch {
                        val productDuplicated = productDao.verifyProduct(productId)
                        if(productDuplicated == null)
                        {
                            productDao.insert(product)
                            Toast.makeText(context, "Se registró el producto satisfactoriamente.", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                            navController.navigate(MenuScreens.ProductsList.route)
                        }
                        else {
                            Toast.makeText(context, "Ya existe un producto registrado con el mismo código.", Toast.LENGTH_SHORT).show()
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
                text = "Registrar producto"
            )
        }
    }
}

@Composable
fun ModifyProductScreen(
    navController: NavHostController,
    productId: String?
) {
    val context = LocalContext.current
    val dataStore = DataPreferences(context = context)
    val name = remember{ mutableStateOf("algo") }
    val description = remember{ mutableStateOf("") }
    val price = remember{ mutableStateOf("") }
    var userId by remember{ mutableStateOf("") }
    userId = dataStore.getUsername.collectAsState(initial = "").value

    val database = Room.databaseBuilder(
        context,
        MyDatabase::class.java,
        "android_database")
        .build()
    val productDao = database.productDao()
    val scope = rememberCoroutineScope()

    var product by remember { mutableStateOf<ProductEntity?>(null) }
    LaunchedEffect(key1 = true) {
        product = productId?.let { productDao.getProduct(it) }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        product?.let { prod ->
            IconTextField(
                name = "*Nombre del producto",
                placeholder = "Ingrese el nombre del producto.",
                icon = Icons.Default.KeyboardArrowRight,
                keyboardType = KeyboardType.Text,
                getText = { name.value = it },
                initText = prod.name
            )
        }
        product?.let { prod ->
            IconTextField(
                name = "Descripción",
                placeholder = "Ingrese una descripción para el producto.",
                icon = Icons.Default.KeyboardArrowRight,
                keyboardType = KeyboardType.Text,
                getText = { description.value = it },
                isDescription = true,
                initText = prod.description
            )
        }
        product?.let { prod ->
            IconTextField(
                name = "*Precio",
                placeholder = "Ingrese el precio del producto.",
                icon = Icons.Default.ShoppingCart,
                keyboardType = KeyboardType.Number,
                getText = { price.value = it },
                initText = prod.unit_price.toString()
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                if (name.value.isEmpty()
                    || price.value.isEmpty()
                ) {
                    Toast.makeText(
                        context,
                        "Tiene algún campo(s) obligatorio sin llenar.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val productUpdate = ProductEntity(productId!!,
                        name.value, description.value, price.value.toDouble(), true, userId)
                    scope.launch {
                        productDao.update(productUpdate)
                        Toast.makeText(
                            context,
                            "Se actualizó el producto satisfactoriamente.",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.popBackStack()
                        //navController.navigate(MenuScreens.ProductsList.route)
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
                text = "Actualizar producto"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddPreview() {
    val navController = rememberNavController()
    AddProductScreen(navController)
}
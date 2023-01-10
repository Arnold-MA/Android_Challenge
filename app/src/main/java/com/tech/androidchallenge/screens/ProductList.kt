package com.tech.androidchallenge.screens

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.room.Room
import com.tech.androidchallenge.database.MyDatabase
import com.tech.androidchallenge.database.ProductEntity
import com.tech.androidchallenge.navigation.MenuScreens
import kotlinx.coroutines.launch

@Composable
fun ProductListScreen(navController: NavHostController) {

    val currentOrientation = LocalConfiguration.current.orientation
    var products by remember { mutableStateOf<List<ProductEntity>?>(null) }

    val context: Context = LocalContext.current
    val database = Room.databaseBuilder(
        context,
        MyDatabase::class.java,
        "android_database")
        .build()
    val productDao = database.productDao()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        products = productDao.getAllProducts()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            products?.let { LandscapeTable(products = it, navController = navController) }
        }
        else {
            products?.let { PortraitTable(products = it, navController = navController) }
        }
    }
}

@Composable
fun LandscapeTable (
    products: List<ProductEntity>,
    navController: NavHostController,
) {
    var openDialog by remember { mutableStateOf(false) }
    var currentProduct by remember { mutableStateOf<ProductEntity?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(all = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            //.fillMaxHeight(0.5f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.05f)
                    .background(color = Color.Gray)
                    .border(width = 1.dp, color = Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Nro",
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.1f)
                    .background(color = Color.Gray)
                    .border(width = 1.dp, color = Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Código",
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .background(color = Color.Gray)
                    .border(width = 1.dp, color = Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Nombre",
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .background(color = Color.Gray)
                    .border(width = 1.dp, color = Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Descripción",
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .background(color = Color.Gray)
                    .border(width = 1.dp, color = Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Precio",
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(1.0f)
                    .background(color = Color.Gray)
                    .border(width = 1.dp, color = Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Acciones",
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
        LazyColumn{
            items(products.size) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    //.fillMaxHeight(0.5f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.05f)
                            .background(color = if (index % 2 == 0) Color.White else Color.LightGray)
                            .border(width = 1.dp, color = Color.Black),
                    ) {
                        Text(text = "${index+1}",
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.1f)
                            .background(color = if (index % 2 == 0) Color.White else Color.LightGray)
                            .border(width = 1.dp, color = Color.Black),
                    ) {
                        Text(text = products[index].productId,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.25f)
                            .background(color = if (index % 2 == 0) Color.White else Color.LightGray)
                            .border(width = 1.dp, color = Color.Black),
                    ) {
                        Text(text = products[index].name,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .background(color = if (index % 2 == 0) Color.White else Color.LightGray)
                            .border(width = 1.dp, color = Color.Black),
                    ) {
                        Text(text = products[index].description,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .background(color = if (index % 2 == 0) Color.White else Color.LightGray)
                            .border(width = 1.dp, color = Color.Black),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(text = "${products[index].unit_price}",
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(1.0f)
                            .background(color = if (index % 2 == 0) Color.White else Color.LightGray)
                            .border(width = 1.dp, color = Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            modifier = Modifier.size(26.dp),
                            onClick = {
                                currentProduct = products[index]
                                openDialog = true
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Settings, contentDescription = "options")
                        }
                    }
                }
            }
        }

        if (openDialog && currentProduct != null) {
            ModalCRUD(
                product = currentProduct,
                setDialog = {openDialog = it},
                navController = navController,
            ) { currentProduct = it }
        }
    }
}

@Composable
fun PortraitTable (
    products: List<ProductEntity>,
    navController: NavHostController,
) {
    var openDialog by remember { mutableStateOf(false) }
    var currentProduct by remember { mutableStateOf<ProductEntity?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(all = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
                //.fillMaxHeight(0.5f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.1f)
                    //.padding(all = 4.dp)
                    .background(color = Color.Gray)
                    .border(width = 1.dp, color = Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Nro",
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    //.padding(all = 4.dp)
                    .background(color = Color.Gray)
                    .border(width = 1.dp, color = Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Nombre",
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    //.padding(all = 4.dp)
                    .background(color = Color.Gray)
                    .border(width = 1.dp, color = Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Precio",
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(1.0f)
                    //.padding(all = 4.dp)
                    .background(color = Color.Gray)
                    .border(width = 1.dp, color = Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Acciones",
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
        LazyColumn{
            items(products.size) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                        //.fillMaxHeight(0.5f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.1f)
                            .background(color = if (index % 2 == 0) Color.White else Color.LightGray)
                            .border(width = 1.dp, color = Color.Black),
                    ) {
                        Text(text = "${index+1}",
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .background(color = if (index % 2 == 0) Color.White else Color.LightGray)
                            .border(width = 1.dp, color = Color.Black),
                    ) {
                        Text(text = products[index].name,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .background(color = if (index % 2 == 0) Color.White else Color.LightGray)
                            .border(width = 1.dp, color = Color.Black),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(text = "${products[index].unit_price}",
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(1.0f)
                            .background(color = if (index % 2 == 0) Color.White else Color.LightGray)
                            .border(width = 1.dp, color = Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            modifier = Modifier.size(26.dp),
                            onClick = {
                                currentProduct = products[index]
                                openDialog = true
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Settings, contentDescription = "options")
                        }
                    }
                }
            }
        }

        if (openDialog && currentProduct != null) {
            ModalCRUD(
                product = currentProduct,
                setDialog = {openDialog = it},
                navController = navController,
                { currentProduct = it }
            )
        }
    }
}

@Composable
fun ModalCRUD(
    product: ProductEntity?,
    setDialog: (Boolean) -> Unit,
    navController: NavHostController,
    setProduct: (ProductEntity?) -> Unit
) {
    var openInfo by remember { mutableStateOf(false) }
    var openConfirmDelete by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = {
            setProduct(null)
            setDialog(false)
        },
        title = {
            Text(text = "${product?.productId} - ${product?.name}")
        },
        text = {
            Column() {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 4.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = rememberRipple(bounded = true, color = Color.Red),
                            onClick = {
                                openInfo = true
                            }
                        )
                ) {
                    Text(text = "Ver información completa")
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 4.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = rememberRipple(bounded = true, color = Color.Red),
                            onClick = {
                                navController.navigate("${MenuScreens.ProductModify.route}/${product?.productId}")
                                setProduct(null)
                                setDialog(false)
                            }
                        )
                ) {
                    Text(text = "Editar producto")
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 4.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = rememberRipple(bounded = true, color = Color.Red),
                            onClick = {
                                openConfirmDelete = true
                            }
                        )
                ) {
                    Text(text = "Eliminar producto")
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    setProduct(null)
                    setDialog(false)
                }
            ) {
                Text("Cancelar")
            }
        },
        /*dismissButton = {
            TextButton(
                onClick = {
                    openDialog.value = false
                }
            ) {
                Text("Dismiss")
            }
        }*/
    )
    if (openInfo) {
        ModalInfo(
            product = product,
            setInfo = {openInfo = it}
        )
    }
    if (openConfirmDelete) {
        ConfirmDelete(
            product = product,
            setConfirm = {openConfirmDelete = it},
            setDialog = setDialog,
            setProduct = setProduct,
            navController = navController
        )
    }
}

@Composable
fun ModalInfo(
    product: ProductEntity?,
    setInfo: (Boolean) -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            setInfo(false)
        },
        title = {
            Text(text = "Información de producto")
        },
        text = {
            Column() {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 4.dp)
                ){
                    Box(
                        modifier = Modifier.fillMaxWidth(0.3f),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = "Código:",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Right
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Box() {
                        Text(
                            text = "${product?.productId}"
                        )
                    }
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 4.dp)
                ){
                    Box(
                        modifier = Modifier.fillMaxWidth(0.3f),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = "Nombre:",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Right
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Box() {
                        Text(
                            text = "${product?.name}"
                        )
                    }
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 4.dp)
                ){
                    Box(
                        modifier = Modifier.fillMaxWidth(0.3f),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = "Descripción:",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Right
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Box() {
                        Text(
                            text = "${product?.description}"
                        )
                    }
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 4.dp)
                ){
                    Box(
                        modifier = Modifier.fillMaxWidth(0.3f),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = "Precio:",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Right
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Box() {
                        Text(
                            text = "${product?.unit_price}"
                        )
                    }
                }

            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    setInfo(false)
                }
            ) {
                Text("Ok!")
            }
        },
    )
}

@Composable
fun ConfirmDelete(
    product: ProductEntity?,
    setConfirm: (Boolean) -> Unit,
    setDialog: (Boolean) -> Unit,
    setProduct: (ProductEntity?) -> Unit,
    navController: NavHostController
) {
    val database = Room.databaseBuilder(
        LocalContext.current,
        MyDatabase::class.java,
        "android_database")
        .build()
    val productDao = database.productDao()
    val scope = rememberCoroutineScope()
    AlertDialog(
        onDismissRequest = {
            setConfirm(false)
        },
        title = {
            Text(text = "Eliminar producto")
        },
        text = {
            Text(text = "¿Está seguro de eliminar el producto ${product?.productId} - ${product?.name}?")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    scope.launch {
                        if (product != null) {
                            productDao.delete(product)
                        }
                    }
                    navController.popBackStack()
                    navController.navigate(MenuScreens.ProductsList.route)
                    /*setProduct(null)
                    setDialog(false)
                    setConfirm(false)*/
                }
            ) {
                Text("Sí")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    setConfirm(false)
                }
            ) {
                Text("No")
            }
        }
    )
}
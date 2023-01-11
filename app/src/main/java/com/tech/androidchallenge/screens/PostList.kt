package com.tech.androidchallenge.screens

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.tech.androidchallenge.apirest.Post
import com.tech.androidchallenge.apirest.PostAPI
import com.tech.androidchallenge.apirest.PostService
import com.tech.androidchallenge.database.ProductEntity
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun PostList(navController: NavHostController) {
    val currentOrientation = LocalConfiguration.current.orientation

    val postEP: PostService = PostService()
    var posts by remember { mutableStateOf<List<Post>?>(null) }

    LaunchedEffect(key1 = true){
        posts = postEP.getAllPosts()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (posts != null){
            if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                LandscapeTablePost(posts = posts!!, navController = navController)
            }
            else {
                PortraitTablePost(posts = posts!!, navController = navController)
            }
        }
    }
}

@Composable
fun LandscapeTablePost(posts: List<Post>, navController: NavHostController) {
    var openDialog by remember { mutableStateOf(false) }
    var currentPost by remember { mutableStateOf<Post?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 4.dp),
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
                Text(text = "Título",
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
                Text(text = "Usuario",
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
            items(posts.size) { index ->
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
                        Text(text = "${posts[index].id}",
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.25f)
                            .background(color = if (index % 2 == 0) Color.White else Color.LightGray)
                            .border(width = 1.dp, color = Color.Black),
                    ) {
                        Text(text = posts[index].title,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .background(color = if (index % 2 == 0) Color.White else Color.LightGray)
                            .border(width = 1.dp, color = Color.Black),
                    ) {
                        Text(text = posts[index].body,
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
                        Text(text = "${posts[index].userId}",
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
                                currentPost = posts[index]
                                openDialog = true
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Settings, contentDescription = "options")
                        }
                    }
                }
            }
        }

        if (openDialog && currentPost != null) {
            ModalCRUDPost(
                post = currentPost,
                setDialog = {openDialog = it},
                navController = navController,
            ) { currentPost = it }
        }
    }
}

@Composable
fun PortraitTablePost(posts: List<Post>, navController: NavHostController) {
    var openDialog by remember { mutableStateOf(false) }
    var currentPost by remember { mutableStateOf<Post?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 4.dp),
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
                Text(text = "Título",
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
                Text(text = "Usuario",
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
            items(posts.size) { index ->
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
                        Text(text = posts[index].title,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .fillMaxHeight()
                            .background(color = if (index % 2 == 0) Color.White else Color.LightGray)
                            .border(width = 1.dp, color = Color.Black),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(text = "${posts[index].userId}",
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
                                currentPost = posts[index]
                                openDialog = true
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Settings, contentDescription = "options")
                        }
                    }
                }
            }
        }

        if (openDialog && currentPost != null) {
            ModalCRUDPost(
                post = currentPost,
                setDialog = {openDialog = it},
                navController = navController,
                { currentPost = it }
            )
        }
    }
}

@Composable
fun ModalCRUDPost(
    post: Post?,
    setDialog: (Boolean) -> Unit,
    navController: NavHostController,
    setProduct: (Post?) -> Unit
) {

}
package com.tech.androidchallenge

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.runtime.saveable.rememberSaveable


@Composable
fun IconTextField(
    name: String,
    placeholder: String,
    icon: ImageVector,
    keyboardType: KeyboardType,
    getText:(String) -> Unit,
    isDescription: Boolean = false,
    initText: String = ""
) {
    /*var text by remember { mutableStateOf(TextFieldValue("")) }
    getText(text.text)*/
    var text by rememberSaveable { mutableStateOf(initText) }
    getText(text)

    OutlinedTextField(
        value = text,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        ),
        leadingIcon = {Icon(imageVector = icon, contentDescription = "Text Field")},
        label = { Text(text = name)},
        placeholder = { Text(text = placeholder)},
        onValueChange = {
            text = it
        },
        modifier = Modifier.fillMaxWidth(0.80f),
        singleLine = !isDescription
    )
}

@Composable
fun PasswordTextField(
    getPassword:(String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val showPassword = remember{ mutableStateOf(false) }
    /*var text by remember { mutableStateOf(TextFieldValue("")) }
    getPassword(text.text)*/
    var text by rememberSaveable { mutableStateOf("") }
    getPassword(text)

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
        },
        leadingIcon = {Icon(imageVector = Icons.Default.Lock, contentDescription = "Password")},
        label = { Text(text = "Contraseña")},
        placeholder = { Text(text = "Ingrese su contraseña.")},
        keyboardOptions = KeyboardOptions(
            autoCorrect = true,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        modifier = Modifier.fillMaxWidth(0.80f),
        singleLine = true,
        visualTransformation = if (showPassword.value) {
            VisualTransformation.None
        }
        else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            val (icon, iconColor) = if (showPassword.value) {
                Pair(
                    painterResource(id = R.drawable.ic_visibility),
                    colorResource(id = R.color.purple_500)
                )
            }
            else {
                Pair(
                    painterResource(id = R.drawable.ic_visibility_off),
                    colorResource(id = R.color.purple_700)
                )
            }
            IconButton(onClick = { showPassword.value = !showPassword.value }) {
                Icon(painter = icon, contentDescription = "Visibility", tint = iconColor)
            }
        }
    )
}
package com.tech.androidchallenge

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue


@Composable
fun IconTextField(
    name: String,
    placeholder: String,
    icon: ImageVector,
    keyboardType: KeyboardType,
    getText:(String) -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    getText(text.text)

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
        modifier = Modifier.fillMaxWidth(0.80f)
    )
}

@Composable
fun PasswordTextField(
    getPassword:(String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val showPassword by remember{ mutableStateOf(false) }
    getPassword(text.text)

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
        modifier = Modifier.fillMaxWidth(0.80f)
    )
}
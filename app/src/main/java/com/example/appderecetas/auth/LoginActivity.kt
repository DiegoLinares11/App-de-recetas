package com.example.appderecetas.auth

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appderecetas.navigation.AppScreens

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (email == "info@koalit.dev" && password == "koalit123") {
                saveSession(context)
                navController.navigate(AppScreens.MainScreen.route)
            } else {
                // Mostrar un mensaje de error
            }
        }) {
            Text("Iniciar Sesión")
        }
    }
}

private fun saveSession(context: Context) {
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean("isLoggedIn", true)
    editor.apply()
}

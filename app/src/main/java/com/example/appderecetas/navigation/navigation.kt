package com.example.appderecetas.navigation

import android.content.Context
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appderecetas.ui.screens.AddRecipeScreen
import com.example.appderecetas.ui.screens.MainScreen
import com.example.appderecetas.ui.screens.auth.LoginScreen

@Composable
fun AppNavigation(context: Context) {
    val navController = rememberNavController()
    val startDestination = remember { getStartDestination(context) }

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                context = context
            )
        }
        composable("main") {
            MainScreen(
                navController = navController,
                context = context
            )
        }
        composable("add_recipe") {
            AddRecipeScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}

private fun getStartDestination(context: Context): String {
    val prefs = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    return if (prefs.getBoolean("isLoggedIn", false)) "main" else "login"
}
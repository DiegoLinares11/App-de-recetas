package com.example.appderecetas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appderecetas.ui.screens.auth.LoginScreen
import com.example.appderecetas.ui.screens.MainScreen

sealed class AppScreens(val route: String) {
    object LoginScreen : AppScreens("login")
    object MainScreen : AppScreens("main")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.LoginScreen.route
    ) {
        composable(AppScreens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(AppScreens.MainScreen.route) {
            MainScreen(navController)
        }
    }
}
package com.example.appderecetas

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.appderecetas.navigation.AppNavigation
import com.example.appderecetas.ui.theme.AppDeRecetasTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppDeRecetasTheme {
                AppNavigation()
            }
        }
    }
}
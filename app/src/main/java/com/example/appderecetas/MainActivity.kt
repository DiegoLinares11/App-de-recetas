package com.example.appderecetas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.appderecetas.navigation.AppNavigation
import com.example.appderecetas.ui.theme.AppDeRecetasTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppDeRecetasTheme {
                AppNavigation(context = this@MainActivity)
            }
        }
    }
}
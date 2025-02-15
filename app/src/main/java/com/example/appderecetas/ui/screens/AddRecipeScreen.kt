package com.example.appderecetas.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appderecetas.viewmodel.RecipeViewModel

@Composable
fun AddRecipeScreen(onBack: () -> Unit) {
    val viewModel: RecipeViewModel = hiltViewModel()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var isTimeValid by remember { mutableStateOf(true) }
    val isFormValid = title.isNotBlank() &&
            description.isNotBlank() &&
            time.toIntOrNull()?.let { it > 0 } ?: false


    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = time,
            onValueChange = {
                time = it
                isTimeValid = it.toIntOrNull() != null
            },
            label = { Text("Tiempo (minutos)") },
            isError = !isTimeValid
        )

        if (!isTimeValid) {
            Text("Tiempo inválido", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.insertRecipe(
                    title = title,
                    description = description,
                    time = time.toIntOrNull() ?: 0
                )

                onBack()
            },
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Receta")
        }
    }
}
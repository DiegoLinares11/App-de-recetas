package com.example.appderecetas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appderecetas.ui.screens.models.Recipe

@Composable
fun MainScreen(navController: NavController) {
    val recipes = listOf(
        Recipe(1, "Receta 1", "Descripción 1", 30, false),
        Recipe(2, "Receta 2", "Descripción 2", 45, true)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Listado de Recetas", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        RecipeList(recipes)
    }
}

@Composable
fun RecipeList(recipes: List<Recipe>) {
    LazyColumn {
        items(recipes) { recipe ->
            RecipeItem(recipe)
        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(recipe.title, style = MaterialTheme.typography.titleLarge)
            Text(recipe.description)
            Text("Tiempo: ${recipe.preparationTime} min")
        }
    }
}
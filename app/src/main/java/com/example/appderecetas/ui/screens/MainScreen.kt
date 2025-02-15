package com.example.appderecetas.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appderecetas.data.entity.RecipeEntity
import com.example.appderecetas.viewmodel.RecipeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Logout

@Composable
fun MainScreen(navController: NavController, context: Context) {
    val viewModel: RecipeViewModel = hiltViewModel()
    val recipes by viewModel.allRecipes.observeAsState(emptyList())
    var showFavorites by remember { mutableStateOf(false) }
    var sortAscending by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Listado de Recetas", style = MaterialTheme.typography.headlineMedium)


            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = { showFavorites = !showFavorites }) {
                    Icon(
                        imageVector = if (showFavorites) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favoritos"
                    )
                }

                IconButton(onClick = {
                    sortAscending = !sortAscending
                    viewModel.sortRecipes(sortAscending)
                }) {
                    Icon(
                        imageVector = if (sortAscending) Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward,
                        contentDescription = "Ordenar"
                    )
                }

                Button(onClick = { navController.navigate("add_recipe") }) {
                    Text("Agregar")
                }
            }
        }

        // Lista de recetas
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(if (showFavorites) viewModel.favorites.value ?: emptyList()
            else viewModel.sortedRecipes.value ?: recipes) { recipe ->
                RecipeItem(
                    recipe = recipe,
                    onFavoriteClick = { viewModel.toggleFavorite(recipe) }
                )
            }
        }
    }
}

@Composable
fun RecipeItem(recipe: RecipeEntity, onFavoriteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(recipe.title, style = MaterialTheme.typography.titleLarge)
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (recipe.isFavorite) Icons.Filled.Favorite
                        else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorito"
                    )
                }
            }
            Text(recipe.description)
            Text("Tiempo: ${recipe.preparationTime} min")
        }
    }
}
// Credenciales
//Correo: info@koalit.dev
// Contraseña : koalit123
/*
IconButton(onClick = {
    context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE).edit().clear().apply()
    navController.navigate("login")
}) {
    Icon(Icons.Default.Logout, "Cerrar sesión")
}*/
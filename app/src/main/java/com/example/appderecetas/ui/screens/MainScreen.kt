package com.example.appderecetas.ui.screens

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Logout
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import coil.compose.rememberImagePainter
import com.example.appderecetas.R

@Composable
fun MainScreen(navController: NavController, context: Context) {
    val viewModel: RecipeViewModel = hiltViewModel()
    val recipes by viewModel.allRecipes.observeAsState(emptyList())
    var showFavorites by remember { mutableStateOf(false) }
    var sortAscending by remember { mutableStateOf(true) }
    val favorites by viewModel.favorites.observeAsState(emptyList()) // Observar favoritos
    val sortedRecipes by viewModel.sortedRecipes.observeAsState(emptyList()) // Observar recetas ordenadas

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.verde)) // Color de la empresa jeje
            .padding(top = 24.dp)
    ) {
        // Encabezado con logo y título
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo), // logo de la empresa jeje
                contentDescription = "Logo",
                modifier = Modifier.size(48.dp)
            )
            Text("Listado de Recetas", style = MaterialTheme.typography.headlineMedium, color = Color.White)
            IconButton(onClick = {
                context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE).edit().clear().apply()
                navController.navigate("login")
            }) {
                Icon(Icons.Default.Logout, contentDescription = "Cerrar sesión", tint = Color.White)
            }
        }

        // Barra de acciones
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { showFavorites = !showFavorites }) {
                Icon(
                    imageVector = if (showFavorites) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favoritos",
                    tint = Color.White
                )
            }
            IconButton(onClick = {
                sortAscending = !sortAscending
                viewModel.sortRecipes(sortAscending)
            }) {
                Icon(
                    imageVector = if (sortAscending) Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward,
                    contentDescription = "Ordenar",
                    tint = Color.White
                )
            }
            Button(
                onClick = { navController.navigate("add_recipe") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
            ) {
                Text("Agregar")
            }
        }

        // Lista de recetas
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(
                if (showFavorites) favorites else sortedRecipes.ifEmpty { recipes }
            ) { recipe ->
                RecipeItem(
                    recipe = recipe,
                    onFavoriteClick = { viewModel.toggleFavorite(recipe) },
                    onDeleteClick = { viewModel.deleteRecipe(recipe) } // Nueva función
                )
            }
        }
    }
}

@Composable
fun RecipeItem(
    recipe: RecipeEntity,
    onFavoriteClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Eliminar receta") },
            text = { Text("¿Estás seguro de querer eliminar esta receta?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDeleteClick()
                    }
                ) {
                    Text("Eliminar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E2A2D))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Imagen en su propia sección
            recipe.imageUri?.let { uri ->
                Image(
                    painter = rememberImagePainter(Uri.parse(uri)),
                    contentDescription = "Imagen de receta",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Fila para título y botones
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    recipe.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )

                Row {
                    IconButton(onClick = onFavoriteClick) {
                        Icon(
                            imageVector = if (recipe.isFavorite) Icons.Filled.Favorite
                            else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = Color.Red
                        )
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = Color.Red
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Descripción y tiempo
            Text(recipe.description, color = Color.White)
            Text("Tiempo: ${recipe.preparationTime} min", color = Color.White)
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
package com.example.appderecetas.ui.screens


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appderecetas.viewmodel.RecipeViewModel
import com.example.appderecetas.R
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import coil.compose.rememberImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(onBack: () -> Unit) {
    val viewModel: RecipeViewModel = hiltViewModel()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var isTimeValid by remember { mutableStateOf(true) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    // Launcher para seleccionar imagen

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val isFormValid = title.isNotBlank() &&
            description.isNotBlank() &&
            time.toIntOrNull()?.let { it > 0 } ?: false

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.verde))
            .padding(16.dp)
    ) {
        // Logo centrado
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(80.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Encabezado con botón atrás
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Atrás",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                "Agregar Receta",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    // Botón para seleccionar imagen
        Button(
            onClick = { pickImageLauncher.launch("image/*") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Icon(Icons.Default.Photo, contentDescription = "Imagen")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Seleccionar imagen")
        }

        // Previsualización de imagen seleccionada
        imageUri?.let { uri ->
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = rememberImagePainter(uri),
                contentDescription = "Previsualización",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale =  ContentScale.Crop
            )

            // Botón para quitar imagen
            TextButton(
                onClick = { imageUri = null },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Quitar imagen", color = Color.Red)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Campo de Título
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título", color = Color.White) },
            leadingIcon = {
                Icon(
                    Icons.Filled.Title,
                    contentDescription = "Título",
                    tint = Color.White
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.LightGray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Descripción
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción", color = Color.White) },
            leadingIcon = {
                Icon(
                    Icons.Filled.Description,
                    contentDescription = "Descripción",
                    tint = Color.White
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.LightGray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Tiempo
        OutlinedTextField(
            value = time,
            onValueChange = {
                time = it
                isTimeValid = it.toIntOrNull() != null
            },
            label = { Text("Tiempo (minutos)", color = Color.White) },
            leadingIcon = {
                Icon(
                    Icons.Filled.Timer,
                    contentDescription = "Tiempo",
                    tint = Color.White
                )
            },
            isError = !isTimeValid,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.LightGray
            )
        )

        if (!isTimeValid) {
            Text(
                "Tiempo inválido",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de Guardar Receta
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isFormValid) Color.White else Color(0xFF1E2A2D),
                contentColor = if (isFormValid) Color.Black else Color.White,
                disabledContainerColor = Color(0xFF1E2A2D), // Color visible cuando está deshabilitado
                disabledContentColor = Color.White // Texto visible cuando está deshabilitado
            )
        ) {
            Text("Guardar Receta")
        }

    }

}

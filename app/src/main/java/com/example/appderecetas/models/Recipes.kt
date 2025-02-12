package com.example.appderecetas.models

data class Recipe(
    val id: Int,
    val title: String,
    val description: String,
    val preparationTime: Int,
    val isFavorite: Boolean,
    val imageUri: String? = null
)
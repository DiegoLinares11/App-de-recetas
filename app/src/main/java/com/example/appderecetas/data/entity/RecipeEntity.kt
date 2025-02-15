package com.example.appderecetas.data.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val preparationTime: Int,
    val isFavorite: Boolean,
    val imageUri: String? = null  // Opcional para la foto
)
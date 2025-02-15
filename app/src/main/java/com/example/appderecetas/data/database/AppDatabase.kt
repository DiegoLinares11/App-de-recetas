package com.example.appderecetas.data.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appderecetas.data.dao.RecipeDao
import com.example.appderecetas.data.entity.RecipeEntity

@Database(
    entities = [RecipeEntity::class],
    version = 1,
    exportSchema = false  // Opcional: evita exportar esquema para historial
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

}
package com.example.appderecetas.data.database
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appderecetas.data.dao.RecipeDao
import com.example.appderecetas.data.entity.RecipeEntity

@Database(
    entities = [RecipeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

}
package com.example.appderecetas.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.appderecetas.data.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    // Devuelve Flow para observar cambios en tiempo real
    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<RecipeEntity>>


    @Insert
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Update
    suspend fun updateRecipe(recipe: RecipeEntity)

    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)

    @Query("SELECT * FROM recipes ORDER BY preparationTime ASC")
    suspend fun getRecipesSortedByTimeAsc(): List<RecipeEntity>

    @Query("SELECT * FROM recipes ORDER BY preparationTime DESC")
    suspend fun getRecipesSortedByTimeDesc(): List<RecipeEntity>

    @Query("SELECT * FROM recipes WHERE isFavorite = 1")
    fun getFavoriteRecipes(): Flow<List<RecipeEntity>>
}

package com.example.appderecetas.repository

import com.example.appderecetas.data.dao.RecipeDao
import com.example.appderecetas.data.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val recipeDao: RecipeDao) {
    // Devuelve Flow para consultas de lectura
    fun getAllRecipes(): Flow<List<RecipeEntity>> = recipeDao.getAllRecipes()

    // Operaciones de escritura siguen siendo suspend
    suspend fun insertRecipe(recipe: RecipeEntity) = recipeDao.insertRecipe(recipe)

    fun getFavorites(): Flow<List<RecipeEntity>> = recipeDao.getFavoriteRecipes()
}
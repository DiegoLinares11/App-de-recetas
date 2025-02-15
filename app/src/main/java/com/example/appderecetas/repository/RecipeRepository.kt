package com.example.appderecetas.repository

import com.example.appderecetas.data.dao.RecipeDao
import com.example.appderecetas.data.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val recipeDao: RecipeDao
) {
    fun getAllRecipes(): Flow<List<RecipeEntity>> = recipeDao.getAllRecipes()

    fun getFavorites(): Flow<List<RecipeEntity>> = recipeDao.getFavoriteRecipes()

    suspend fun getRecipesSortedByTimeAsc(): List<RecipeEntity> =
        recipeDao.getRecipesSortedByTimeAsc()

    suspend fun getRecipesSortedByTimeDesc(): List<RecipeEntity> =
        recipeDao.getRecipesSortedByTimeDesc()

    suspend fun insertRecipe(recipe: RecipeEntity) = recipeDao.insertRecipe(recipe)

    suspend fun updateRecipe(recipe: RecipeEntity) = recipeDao.updateRecipe(recipe)
}


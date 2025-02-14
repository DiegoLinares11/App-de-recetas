package com.example.appderecetas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.appderecetas.data.database.AppDatabase
import com.example.appderecetas.data.entity.RecipeEntity
import com.example.appderecetas.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RecipeRepository

    // LiveData para observar en la UI
    val allRecipes: LiveData<List<RecipeEntity>>
        get() = repository.getAllRecipes().asLiveData()

    init {
        val db = AppDatabase.getInstance(application)
        repository = RecipeRepository(db.recipeDao())
    }

    fun insertRecipe(title: String, description: String, time: Int) {
        viewModelScope.launch {
            val recipe = RecipeEntity(
                title = title,
                description = description,
                preparationTime = time,
                isFavorite = false
            )
            repository.insertRecipe(recipe)
        }
    }
}
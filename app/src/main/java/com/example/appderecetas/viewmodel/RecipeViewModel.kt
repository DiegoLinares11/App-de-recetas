package com.example.appderecetas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.appderecetas.data.database.AppDatabase
import com.example.appderecetas.data.entity.RecipeEntity
import com.example.appderecetas.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _allRecipes = repository.getAllRecipes().asLiveData()
    val allRecipes: LiveData<List<RecipeEntity>> get() = _allRecipes

    private val _favorites = repository.getFavorites().asLiveData()
    val favorites: LiveData<List<RecipeEntity>> get() = _favorites

    private val _sortedRecipes = MutableLiveData<List<RecipeEntity>>()
    val sortedRecipes: LiveData<List<RecipeEntity>> get() = _sortedRecipes

    fun toggleFavorite(recipe: RecipeEntity) {
        viewModelScope.launch {
            repository.updateRecipe(recipe.copy(isFavorite = !recipe.isFavorite))
        }
    }

    fun sortRecipes(ascending: Boolean = true) {
        viewModelScope.launch {
            val sorted = if (ascending) {
                repository.getRecipesSortedByTimeAsc()
            } else {
                repository.getRecipesSortedByTimeDesc()
            }
            _sortedRecipes.postValue(sorted)
        }
    }

    fun insertRecipe(title: String, description: String, time: Int) {
        viewModelScope.launch {
            val recipe = RecipeEntity(
                title = title.trim(),
                description = description.trim(),
                preparationTime = time,
                isFavorite = false
            )
            repository.insertRecipe(recipe)
        }
    }
}
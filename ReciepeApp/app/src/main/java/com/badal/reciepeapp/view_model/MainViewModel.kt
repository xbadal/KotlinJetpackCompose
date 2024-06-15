package com.badal.reciepeapp.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.badal.reciepeapp.model.CategoryModel
import com.badal.reciepeapp.services.recipeService
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _categoryState = mutableStateOf(RecipeState())

    val categoriesState: State<RecipeState> = _categoryState

    data class RecipeState(
        val loading: Boolean = true,
        val list: List<CategoryModel> = emptyList(),
        val error: String? = null
    )


    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = recipeService.getCategories()

                _categoryState.value = _categoryState.value.copy(
                    loading = false,
                    error = null,
                    list = response.categories,
                )
            } catch (e: Exception) {
                _categoryState.value =
                    _categoryState.value.copy(
                        loading = false,
                        error = "Error Fetching Category ${e.message}"
                    )
            }
        }
    }
}
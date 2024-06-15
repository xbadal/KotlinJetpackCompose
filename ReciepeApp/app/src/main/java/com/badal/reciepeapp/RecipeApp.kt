package com.badal.reciepeapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.badal.Screen
import com.badal.reciepeapp.model.CategoryModel
import com.badal.reciepeapp.view_model.MainViewModel


@Composable
fun RecipeApp(navController: NavHostController) {
    val recipeViewModel: MainViewModel = viewModel()
    val viewState by recipeViewModel.categoriesState


    NavHost(navController = navController, startDestination = Screen.RecipeScreen.route) {
        composable(route = Screen.RecipeScreen.route) {
            RecipeScreen(viewState = viewState, navigateToDetails = {
                navController.currentBackStackEntry?.savedStateHandle?.set("cat", it);
                navController.navigate(Screen.CategoryDetailsScreen.route)
            })
        }

        composable(route = Screen.CategoryDetailsScreen.route) {
            val category =
                navController.previousBackStackEntry?.savedStateHandle?.get<CategoryModel>("cat")
                    ?: CategoryModel("", "", "", "")
            CategoryDetailsScreen(category = category);
        }
    }
}
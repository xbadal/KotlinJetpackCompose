package com.badal

sealed class Screen(val route: String) {
object RecipeScreen : Screen("recipeScreen")
object CategoryDetailsScreen : Screen("categoryDetailsScreen")
}
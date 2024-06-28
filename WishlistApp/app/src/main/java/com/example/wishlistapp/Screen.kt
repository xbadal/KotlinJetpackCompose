package com.example.wishlistapp

sealed class Screen(val route: String) {
    object  HomeScreen : Screen(route = "home_screen")
    object  AddScreen : Screen(route = "a_screen")
}
package com.example.wishlistapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title: String, showBackButton: Boolean = true, onBackNavClicked: () -> Unit = {}) {

    val navigationItem: (@Composable () -> Unit) = {
       if(showBackButton){
           IconButton(onClick = { onBackNavClicked() }) {
               Icon(
                   imageVector = Icons.AutoMirrored.Default.ArrowBack, tint = Color.White, contentDescription = "Back " +
                           "navigation"
               )
           }
       }

    }


    TopAppBar(
        title = { Text(text = title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Red,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        navigationIcon = navigationItem
    )
}
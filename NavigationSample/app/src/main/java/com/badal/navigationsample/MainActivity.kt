package com.badal.navigationsample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.badal.navigationsample.ui.SecondScreen
import com.badal.navigationsample.ui.theme.NavigationSampleTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationSampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    MyApp()
                }
            }
        }
    }
}


@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "firstscreen") {

        composable("firstscreen") {
            FirstScreen { name ->
                navController.navigate("secondscreen/$name")
            }
        }
        composable("secondscreen/{name}") {
            val name = it.arguments?.getString("name") ?: "No Name"
            SecondScreen(name) {
                navController.navigate("firstscreen")
            }
        }
    }
}

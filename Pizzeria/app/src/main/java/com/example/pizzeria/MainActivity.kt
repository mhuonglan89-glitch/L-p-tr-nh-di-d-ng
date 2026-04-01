package com.example.pizzeria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Mynavigation()
        }
    }
}

@Composable
fun Mynavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Signin.rout) {
        composable(Screen.Signin.rout) { SignIn(navController) }
        composable(Screen.Home.rout) { HomeScreen(navController) }
    }
}
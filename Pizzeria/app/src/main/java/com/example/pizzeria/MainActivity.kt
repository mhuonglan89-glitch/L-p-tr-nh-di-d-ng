package com.example.pizzeria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.database.FirebaseDatabase

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
        composable(Screen.Admin.rout) { AdminScreen(navController) }
        // Khai báo chính xác link database của Hương Lan
        val database =
            FirebaseDatabase.getInstance("https://pizzeria-4f8b7-default-rtdb.firebaseio.com/")
                .getReference("users")
    }
}
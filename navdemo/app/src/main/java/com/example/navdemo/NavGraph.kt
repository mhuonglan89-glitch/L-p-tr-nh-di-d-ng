package com.example.navdemo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login" // Điểm bắt đầu là Login [cite: 108]
    ) {
        // Màn hình Login [cite: 109]
        composable("login") {
            LoginScreen(navController)
        }
        // Màn hình Home [cite: 110]
        composable("home") {
            HomeScreen(navController)
        }
        // Màn hình Profile [cite: 111]
        composable("profile") {
            ProfileScreen(navController)
        }
    }
}
package com.example.pizzeria

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(navController: NavController) {
    val firebaseAuth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFFBC02D)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text("PIZZERIA", style = MaterialTheme.typography.displayLarge, color = Color(0xFFB71C1C), fontWeight = FontWeight.Bold)
        Text("Delivering Deliciousness right to your door!", modifier = Modifier.padding(16.dp))

        Button(onClick = { /* Logic */ }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB71C1C))) {
            Text("START ORDER")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                firebaseAuth.signOut() // Đăng xuất Firebase
                navController.navigate(Screen.Signin.rout) {
                    popUpTo(Screen.Home.rout) { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("SignOut")
        }

        // Ảnh người giao hàng load từ Internet (Thư viện Coil)
        Image(
            painter = rememberAsyncImagePainter("https://img.freepik.com/free-photo/delivery-man-with-pizza-boxes-scooter_23-2148770213.jpg"),
            contentDescription = null,
            modifier = Modifier.fillMaxSize().padding(top = 20.dp)
        )
    }
}
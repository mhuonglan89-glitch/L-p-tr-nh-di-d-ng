package com.example.navdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navdemo.ui.theme.NavdemoTheme
import androidx.navigation.NavGraph.Companion.findStartDestination
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavdemoTheme {
                // Khởi tạo NavController để quản lý việc chuyển màn hình
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        // Gọi NavGraph để điều hướng giữa các màn hình
                        AppNavGraph(navController = navController)
                    }
                }
            }
        }
    }
}

// --- 1. ĐỊNH NGHĨA SƠ ĐỒ ĐIỀU HƯỚNG (NAV GRAPH) ---
@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login" // Điểm bắt đầu là màn hình Login
    ) {
        composable("login") { LoginScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
    }
}

// --- 2. CÁC MÀN HÌNH CHI TIẾT THEO SƠ ĐỒ ---

@Composable
fun LoginScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("LOGIN SCREEN", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            // Chuyển sang Home và xóa sạch Login khỏi bộ nhớ (Back Stack)
            navController.navigate("home") {
                popUpTo("login") { inclusive = true } //
            }
        }) {
            Text("LOGIN")
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("HOME SCREEN", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            // Chuyển màn hình sang Profile bình thường
            navController.navigate("profile")
        }) {
            Text("GO TO PROFILE")
        }
    }
}

@Composable
fun ProfileScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("PROFILE SCREEN", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            // Quay lại Home và xóa chính màn hình Profile hiện tại khỏi stack
            navController.navigate("home") {
                popUpTo("home") { inclusive = true }
            }
        }) {
            Text("BACK TO HOME")
        }
    }
}
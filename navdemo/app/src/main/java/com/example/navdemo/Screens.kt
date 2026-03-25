import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// 1. Màn hình Login
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
            // Theo sơ đồ: Chuyển sang Home và xóa sạch Login [cite: 3, 4]
            navController.navigate("home") {
                popUpTo("login") { inclusive = true } // inclusive = true xóa Login khỏi stack [cite: 30, 130]
            }
        }) {
            Text("LOGIN")
        }
    }
}

// 2. Màn hình Home
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
            // Theo sơ đồ: Đi tới Profile [cite: 6, 151]
            navController.navigate("profile")
        }) {
            Text("GO TO PROFILE")
        }
    }
}

// 3. Màn hình Profile
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
            // Theo sơ đồ: Quay lại Home và xóa Profile hiện tại [cite: 12, 13, 172]
            navController.navigate("home") {
                popUpTo("home") { inclusive = true }
            }
        }) {
            Text("BACK TO HOME")
        }
    }
}
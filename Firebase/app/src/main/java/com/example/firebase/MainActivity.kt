package com.example.firebase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // Gọi màn hình thử nghiệm
                FirebaseTestScreen()
            }
        }
    }
}

@Composable
fun FirebaseTestScreen() {
    val context = LocalContext.current
    // Biến để lưu trạng thái phản hồi từ Firebase
    var statusText by remember { mutableStateOf("Chưa gửi dữ liệu") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Kiểm tra Firebase Realtime", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = statusText)

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            val database = Firebase.database.reference

            statusText = "Đang gửi..."
            database.child("test_connection").setValue("Chào Hương Lan, Firebase đã chạy!")
                .addOnSuccessListener {
                    statusText = "Ghi dữ liệu THÀNH CÔNG!"
                    Toast.makeText(context, "Đã hiện trên Console!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    statusText = "LỖI: ${it.message}"
                    Toast.makeText(context, "Thất bại: ${it.message}", Toast.LENGTH_LONG).show()
                }
        }) {
            Text("Nhấn để gửi dữ liệu lên Firebase")
        }
    }
}
package com.example.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFFFFF9C4) // Màu vàng nhạt (Lemon Chiffon)
            ) {
                BusinessCard()
            }
        }
    }
}

@Composable
fun BusinessCard() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Phần thân trên: Ảnh và Tên
        val image = painterResource(id = R.drawable.huonglan)
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )
        Text(
            text = "Hương Lan",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF5D4037) // Màu nâu đậm cho nổi trên nền vàng
        )
        Text(
            text = "24IT136",
            fontSize = 18.sp,
            color = Color(0xFFFBC02D) // Màu vàng đậm
        )

        Spacer(modifier = Modifier.height(50.dp))

        // Phần dưới: Thông tin liên hệ đơn giản
        Column {
            ContactRow(icon = Icons.Default.Phone, text = "0984930574")
            ContactRow(icon = Icons.Default.Email, text = "lanmh.24it@vku.udn.vn")
        }
    }
}

@Composable
fun ContactRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(modifier = Modifier.padding(8.dp)) {
        Icon(imageVector = icon, contentDescription = null, tint = Color(0xFF5D4037))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, fontSize = 16.sp)
    }
}
package com.example.woof.ui.theme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(8.dp),
    // Bo góc chéo theo phong cách mới bạn muốn
    medium = RoundedCornerShape(topStart = 24.dp, bottomEnd = 24.dp),
    large = RoundedCornerShape(16.dp)
)
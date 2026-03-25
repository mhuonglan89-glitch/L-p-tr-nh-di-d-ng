package com.example.woof.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Bảng màu cho Chế độ tối (Dark Scheme)
 * Sử dụng các tông màu xanh sáng hơn để đảm bảo độ tương phản trên nền tối.
 */
private val DarkColorScheme = darkColorScheme(
    primary = GreenSecondary,
    secondary = GreenTertiary,
    tertiary = LightCream
)

/**
 * Bảng màu cho Chế độ sáng (Light Scheme) theo yêu cầu của bạn.
 *
 */
private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    secondary = GreenSecondary,
    tertiary = GreenTertiary,
    background = Color.White,
    surfaceVariant = LightCream, // Màu nền cho các thẻ (Cards)
    onSurfaceVariant = DarkText   // Màu chữ trên nền LightCream
)

@Composable
fun WoofTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Đặt mặc định là false để ưu tiên bộ màu xanh tùy chỉnh của bạn thay vì màu hệ thống.
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Quản lý màu sắc của Thanh trạng thái (Status Bar)
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Đặt màu thanh trạng thái trùng với màu Primary của chủ đề
            window.statusBarColor = colorScheme.primary.toArgb()
            // Điều chỉnh icon trên thanh trạng thái (sáng/tối) dựa vào chế độ nền
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Lấy từ file Type.kt
        shapes = Shapes,         // Lấy từ file Shape.kt
        content = content
    )
}
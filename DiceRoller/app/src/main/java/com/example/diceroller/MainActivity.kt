package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.diceroller.ui.theme.DiceRollerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerTheme {
                DiceRollerApp()
            }
        }
    }
}

@Composable
fun DiceRollerApp() {
    DiceWithButtonAndImage(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    var result by remember { mutableStateOf(1) }
    var rotationAngle by remember { mutableStateOf(0f) }

    // Hiệu ứng xoay mượt mà khi đổi số
    val rotation by animateFloatAsState(
        targetValue = rotationAngle,
        animationSpec = tween(durationMillis = 500),
        label = "DiceRotation"
    )

    val imageResource = when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Chỉ giữ lại hình ảnh xúc xắc với hiệu ứng 3D
        Image(
            painter = painterResource(imageResource),
            contentDescription = result.toString(),
            modifier = Modifier
                .size(160.dp)
                .graphicsLayer(
                    rotationZ = rotation,    // Xoay vòng tròn
                    rotationX = 15f,         // Độ nghiêng trục X (tạo cảm giác khối)
                    rotationY = 15f,         // Độ nghiêng trục Y (tạo cảm giác khối)
                    cameraDistance = 12f     // Độ sâu phối cảnh
                )
        )

        Spacer(modifier = Modifier.height(60.dp))

        Button(
            onClick = {
                result = (1..6).random()
                rotationAngle += 360f // Cộng dồn góc xoay để mỗi lần bấm là một lần quay
            }
        ) {
            Text(stringResource(R.string.roll))
        }
    }
}
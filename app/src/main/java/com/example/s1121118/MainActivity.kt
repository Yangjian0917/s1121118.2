package com.example.s1121118

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.s1121118.ui.theme.S1121118Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {
            S1121118Theme {
                AppScreen { finish() }
            }
        }
    }
}

@Composable
fun AppScreen(onExit: () -> Unit) {
    // 定義背景顏色循環列表
    val colors = listOf(
        Color(0xff95fe95),
        Color(0xfffdca0f),
        Color(0xfffea4a4),
        Color(0xffa5dfed)
    )
    var colorIndex by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors[colorIndex]) // 根據索引顯示顏色
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    if (dragAmount > 0) {
                        // 右滑：顏色向後切換
                        colorIndex = (colorIndex + 1) % colors.size
                    } else if (dragAmount < 0) {
                        // 左滑：顏色向前切換
                        colorIndex = (colorIndex - 1 + colors.size) % colors.size
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "2024期末上機考(資管二B 楊世堅)")
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.class_b),
                contentDescription = "Class B Image",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "遊戲持續時間 0 秒")
            Text(text = "您的成績 0 分")
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onExit) {
                Text(text = "結束App")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppScreenPreview() {
    S1121118Theme {
        AppScreen(onExit = {})
    }
}

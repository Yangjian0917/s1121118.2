package com.example.s1121118

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.s1121118.ui.theme.S1121118Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()
        setContent {
            S1121118Theme {
                AppContent(onExitApp = { finish() })
            }
        }
    }
}

@Composable
fun AppContent(onExitApp: () -> Unit) {

    val colors = listOf(
        Color(0xff95fe95),
        Color(0xfffdca0f),
        Color(0xfffea4a4),
        Color(0xffa5dfed)
    )
    var colorIndex by remember { mutableStateOf(0) }
    var elapsedTime by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var imagePositionX by remember { mutableStateOf(0f) }
    val screenWidth = 1080f
    val imageSize = 200f
    val maxImagePositionX = screenWidth - imageSize

    var mariaImage by remember { mutableStateOf(R.drawable.maria2) }


    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (imagePositionX < maxImagePositionX) {
                delay(1000L)
                imagePositionX = (imagePositionX + 50f).coerceAtMost(maxImagePositionX)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors[colorIndex])
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {

                        colorIndex = (colorIndex + 1) % colors.size
                    },
                    onHorizontalDrag = { _, _ ->

                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            Text(text = "2024期末上機考(資管二B 楊世堅)")
            Spacer(modifier = Modifier.height(16.dp))


            Image(
                painter = painterResource(id = R.drawable.class_b),
                contentDescription = "資管二B圖片",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))


            Text(text = "遊戲持續時間：${elapsedTime} 秒")
            Spacer(modifier = Modifier.height(16.dp))


            Text(text = "您的成績：${score} 分")
            Spacer(modifier = Modifier.height(16.dp))


            Button(onClick = onExitApp) {
                Text(text = "結束App")
            }
        }


        Image(
            painter = painterResource(id = mariaImage),
            contentDescription = "瑪利亞圖示",
            modifier = Modifier
                .size(200.dp)
                .offset(x = imagePositionX.dp, y = 600.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            if (colors[colorIndex] == Color(0xff95fe95)) {
                                score++
                            } else {
                                score--
                            }

                            mariaImage = listOf(
                                R.drawable.maria0,
                                R.drawable.maria1,
                                R.drawable.maria2,
                                R.drawable.maria3
                            ).random()
                            imagePositionX = 0f
                        }
                    )
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppContent() {
    S1121118Theme {
        AppContent(onExitApp = {})
    }
}
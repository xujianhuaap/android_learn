package com.skullmind.io.main.pages

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.rotationMatrix
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

object MinePage {
    @Composable
    @Throws(Error::class)
    fun MinePageView(modifier: Modifier) {
        Column(
            modifier = modifier.then(Modifier.fillMaxWidth()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("个人页面", fontSize = 40.sp, textAlign = TextAlign.Center)
            flexBox()
        }

    }

    @Composable
    private fun flexBox() {
        val scaleState = remember {
            mutableStateOf(1f)
        }

        val state = rememberTransformableState { scale, _, _ ->
            scaleState.value *= scale
        }

        val rotateState = remember {
            mutableStateOf(0f)
        }
        LaunchedEffect(key1 = Unit){
            launch {
                while (true){
                    delay(20)
                    rotateState.value += 5f
                }
            }
        }
        Box(
            modifier = Modifier
                .size(100.dp)

                .graphicsLayer {
                    scaleX = scaleState.value
                    scaleY = scaleState.value
                    rotationZ = rotateState.value
                }
                .transformable(state)
                .background(Color.Transparent)
        ) {
            drawLeaf(Color.Yellow, Math.PI / 2)
            drawLeaf(Color.Blue, Math.PI / 2, 90f)
            drawLeaf(Color.Red, Math.PI / 2, 180f)
            drawLeaf(Color.Green, Math.PI / 2, 270f)


        }
    }

    @Composable
    private fun drawLeaf(color: Color, degree: Double, rotate: Float = 0f) {
        Canvas(
            modifier = Modifier
                .size(200.dp)
                .rotate(rotate)
        ) {
            val radius = (size.width / 2) * sin(degree / 2)
            val x = (size.width / 2 - radius * sin(degree / 2)).toFloat()
            val y = (radius * cos(degree / 2)).toFloat()
            val offset = Offset(x, y)
            val path = Path().apply {
                val rect = Rect(offset, radius = radius.toFloat())
                arcTo(rect = rect, -45f, 90f, true)
            }
            drawPath(path = path, color = color)
        }
    }
}
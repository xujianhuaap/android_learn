package com.skullmind.io.main.pages

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
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
import com.skullmind.io.main.widget.SlideView
import com.skullmind.io.main.widget.flexWindMill
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

object MinePage {
    @Composable
    @Throws(Error::class)
    fun MinePageView(modifier: Modifier) {
        SlideView(
            modifier = modifier,
            slideContent = {
                Text(
                    text = "122",
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .fillMaxHeight()
                        .background(MaterialTheme.colors.primary)
                )
            },
            content = {
                Column(
                    modifier = modifier.then(Modifier.fillMaxWidth()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("个人页面", fontSize = 40.sp, textAlign = TextAlign.Center)
                    flexBox()

                }
            })
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
        LaunchedEffect(key1 = Unit) {
            launch {
                while (true) {
                    delay(20)
                    rotateState.value = ((rotateState.value + 5) % 360)
                }
            }
        }
        flexWindMill(scaleState, rotateState, state)
    }


}
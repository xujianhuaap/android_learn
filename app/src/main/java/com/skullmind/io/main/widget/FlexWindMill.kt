package com.skullmind.io.main.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun flexWindMill(
    scaleState: MutableState<Float>,
    rotateState: MutableState<Float>,
    state: TransformableState
) {
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
package com.skullmind.io.main.widget

import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.skullmind.io.main.vo.NoticeVo

@Composable
fun MarqueeView(datas: List<NoticeVo>, initIndex: Int, screenWidth: Float) {


    MarqueeContainer(datas, initIndex = initIndex, screenWidth)

}

@Composable
private fun MarqueeContainer(datas: List<NoticeVo>, initIndex: Int, screenWidth: Float) {

    var index by remember {
        mutableStateOf(initIndex)
    }


    val transition = rememberInfiniteTransition()
    val value by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 4000
                delayMillis =0
            },
            repeatMode = RepeatMode.Restart
        )

    )

    var enable by remember {
        mutableStateOf(true)
    }

    if (value > 0.99f) {
        if(enable){
            enable = false
            index = (index + 1) % datas.size
        }

    }

    if(value < 0.01f){
        enable = true
    }

    Log.d("-->", "value: $value - $index")



    val clickState = remember {
        mutableStateOf(Pair(false, datas[0]))
    }


    val paint by remember {
        mutableStateOf(Paint().apply {
            color = Color.parseColor("#FF000000")
            isAntiAlias = true
            textSize = 40f
        })
    }
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
    ) {
        drawIntoCanvas {
            it.nativeCanvas.drawText(
                datas[index].title,
                value * (screenWidth + paint.measureText(datas[index].title)) - paint.measureText(
                    datas[index].title
                ),
                center.y,
                paint
            )
        }
    }


    showDialog(clickItemState = clickState)
}


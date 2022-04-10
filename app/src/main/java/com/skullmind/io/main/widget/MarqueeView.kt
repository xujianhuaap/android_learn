package com.skullmind.io.main.widget

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.skullmind.io.main.vo.NoticeVo

@Composable
fun MarqueeView(datas: List<NoticeVo>, initIndex: Int) {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 4000
                delayMillis = 100
            },
            repeatMode = RepeatMode.Restart
        )
    )


    MarqueeContainer(alpha, datas, initIndex = initIndex)

}

@Composable
private fun MarqueeContainer(alpha: Float, datas: List<NoticeVo>, initIndex: Int) {

    var index by remember {
        mutableStateOf(initIndex)
    }
    if (alpha >= 1f) {
        index = (index + 1) % datas.size

    }

    val clickState = remember {
        mutableStateOf(Pair<Boolean, NoticeVo>(false, datas[0]))
    }

    Row(modifier = Modifier.padding(start = (LocalConfiguration.current.screenWidthDp * alpha).dp)) {
        Text(
            text = datas[index].title,
            maxLines = 1,
            modifier = Modifier.clickable {
                clickState.value = Pair(true,datas[index])
            }.padding(vertical = 5.dp)
        )
    }

    showDialog(clickItemState = clickState)
}


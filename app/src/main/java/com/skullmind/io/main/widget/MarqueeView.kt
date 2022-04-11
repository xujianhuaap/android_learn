package com.skullmind.io.main.widget

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.skullmind.io.main.vo.NoticeVo
import kotlinx.coroutines.delay

@Composable
fun MarqueeView(datas: List<NoticeVo>, initIndex: Int) {


    MarqueeContainer(datas, initIndex = initIndex)

}

@Composable
private fun MarqueeContainer(datas: List<NoticeVo>, initIndex: Int) {

    var index by remember {
        mutableStateOf(initIndex)
    }

    var paddingOffset by remember {
        mutableStateOf(0f)
    }
    LaunchedEffect(Unit) {
        while (true) {
            delay(15)
            paddingOffset += 0.6f
        }
    }

    val clickState = remember {
        mutableStateOf(Pair(false, datas[0]))
    }

    Row(
        modifier = Modifier
            .padding(
                start = paddingOffset.dp,
            )
    ) {
        Text(
            text = datas[index].title,
            maxLines = 1,
            modifier = Modifier.padding(vertical = 5.dp),
            overflow = TextOverflow.Clip,
            onTextLayout = {
                if (it.size.width == 0) {
                    index = (index + 1) % datas.size
                    paddingOffset = 0f
                }
                Log.d("-->", "text width ${it.size.width}")
            }

        )
    }

    showDialog(clickItemState = clickState)
}


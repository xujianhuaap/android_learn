package com.skullmind.io.pages

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

object ServiceHallPage {
    @Composable
    fun ServiceHallView(modifier: Modifier){
        Text("服务大厅页面",modifier = modifier.then(Modifier.fillMaxWidth()),fontSize = 40.sp,textAlign = TextAlign.Center)
    }
}
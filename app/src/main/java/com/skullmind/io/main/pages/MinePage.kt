package com.skullmind.io.main.pages

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

object MinePage {
    @Composable
    @Throws(Error::class)
    fun MinePageView(modifier: Modifier){
        Text("个人页面",modifier = modifier,fontSize = 40.sp,textAlign = TextAlign.Center)
    }
}
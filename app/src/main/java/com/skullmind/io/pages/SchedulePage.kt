package com.skullmind.io.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.skullmind.io.main.widget.ScheduleItemView.ScheduleItemView
import java.util.*

object SchedulePage {
    @Composable
    fun ScheduleView(modifier: Modifier) {
        Column(
            modifier = modifier
                .then(Modifier.background(color = Color(0xFE2B435A)))
                .fillMaxWidth()
        ) {
            ScheduleItemView(modifier = modifier)
        }

    }






}
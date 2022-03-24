package com.skullmind.io.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.skullmind.io.main.MainViewModel
import com.skullmind.io.main.widget.ScheduleItemView.ScheduleItemView

object SchedulePage {
    @Composable
    fun ScheduleView(modifier: Modifier,viewModel:MainViewModel) {
        val scrollState = rememberLazyListState()
        val flights = viewModel.getFlightInfos()
        LazyColumn(
            state = scrollState,
            contentPadding = PaddingValues(10.dp),
            modifier = modifier.then(Modifier.background(color = Color(0xFE2B435A)))
        ) {
            items(count = flights.size){
                ScheduleItemView(flights[it])
            }


        }

    }


}
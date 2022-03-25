package com.skullmind.io.pages

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.skullmind.io.main.MainViewModel
import com.skullmind.io.main.widget.ScheduleItemView.ScheduleItemView

object SchedulePage {
    @Composable
    fun ScheduleView(modifier: Modifier, viewModel: MainViewModel) {

        val scrollState = rememberLazyListState()
        val flights = viewModel.getFlightInfos()

        AnimatedVisibility(
            visible = isShowAnimated(scrollState),
            enter = expandIn(initialSize = { IntSize(it.width,0)}),
            exit = shrinkOut(targetSize = { IntSize(it.width,0)}),
            content = {
                Text(
                    text = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(color = Color(0xFE2B435A))
                )
            })

        LazyColumn(
            state = scrollState,
            contentPadding = PaddingValues(10.dp),
            modifier = modifier.then(Modifier.background(color = Color(0xFE2B435A)))
        ) {
            items(count = flights.size) {
                ScheduleItemView(flights[it])
            }
        }


    }

    @Composable
    private fun isShowAnimated(scrollState: LazyListState) =
        scrollState.firstVisibleItemIndex == 0 && scrollState.isScrollInProgress && scrollState.firstVisibleItemScrollOffset <= 0


}
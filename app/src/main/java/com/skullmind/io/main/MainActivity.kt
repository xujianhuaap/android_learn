package com.skullmind.io.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.skullmind.io.error.startErrorActivity
import com.skullmind.io.main.vo.NoticeVo
import com.skullmind.io.main.widget.MainBottomNavigationBar.BottomNavigationBar
import com.skullmind.io.pages.MainNavigator
import com.skullmind.io.pages.MainPage.MainPageView
import com.skullmind.io.pages.MinePage.MinePageView
import com.skullmind.io.pages.SchedulePage.ScheduleView
import com.skullmind.io.pages.ServiceHallPage.ServiceHallView
import com.skullmind.io.route.Route
import com.skullmind.io.theme.AppTheme
import com.skullmind.io.theme.Theme


class MainActivity : AppCompatActivity(),MainNavigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels()
        setContent {
            AppTheme(theme = Theme.Sad) {
                mainView(viewModel = viewModel)
            }

        }
    }

    override fun marqueeClick(noticeVo: NoticeVo) {
        if(noticeVo.link == Route.ACTIVITY_ERROR) startErrorActivity(this)
    }

    @Composable
    private fun mainView(viewModel: MainViewModel) {
        val pageState = remember { mutableStateOf(0) }
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom
        ) {
            when(pageState.value){
                1 -> ScheduleView(Modifier.weight(1.0f),viewModel = viewModel)
                2 -> ServiceHallView(Modifier.weight(1.0f))
                3 -> MinePageView(Modifier.weight(1.0f))
                else -> MainPageView(Modifier.weight(1.0f), viewModel,this@MainActivity)
            }

            BottomNavigationBar(initSelectedIndex = 0) {
                pageState.value = it
            }
        }
    }





}

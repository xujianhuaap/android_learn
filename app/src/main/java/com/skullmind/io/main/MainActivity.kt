package com.skullmind.io.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.skullmind.io.error.startErrorActivity
import com.skullmind.io.getScreenWidth
import com.skullmind.io.main.vo.NoticeVo
import com.skullmind.io.main.widget.MainBottomNavigationBar.BottomNavigationBar
import com.skullmind.io.main.pages.MainNavigator
import com.skullmind.io.main.pages.MainPage.MainPageView
import com.skullmind.io.main.pages.MinePage.MinePageView
import com.skullmind.io.main.pages.SchedulePage.ScheduleView
import com.skullmind.io.main.pages.ServiceHallPage.ServiceHallView
import com.skullmind.io.main.view_model.ServiceHallVM
import com.skullmind.io.route.Route
import com.skullmind.io.theme.AppTheme
import com.skullmind.io.theme.Theme


class MainActivity : AppCompatActivity(),MainNavigator {
    private val serviceHallVM by lazy {
        ViewModelProvider(this)[ServiceHallVM::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels()
        setContent {
            AppTheme(theme = Theme.Happy) {
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
                2 -> ServiceHallView(Modifier.weight(1.0f),serviceHallVM)
                3 -> MinePageView(Modifier.weight(1.0f), getScreenWidth(application))
                else -> MainPageView(Modifier.weight(1.0f), viewModel,this@MainActivity)
            }

            BottomNavigationBar(initSelectedIndex = 0) {
                pageState.value = it
            }
        }
    }





}

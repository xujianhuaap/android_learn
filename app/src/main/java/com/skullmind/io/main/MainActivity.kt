package com.skullmind.io.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.skullmind.io.main.widget.MainBottomNavigationBar.BottomNavigationBar
import com.skullmind.io.pages.MainPage.MainPageView
import com.skullmind.io.pages.MinePage.MinePageView
import com.skullmind.io.pages.SchedulePage.ScheduleView
import com.skullmind.io.pages.ServiceHallPage.ServiceHallView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels()
        setContent {
            mainView(viewModel = viewModel)
        }
    }

    @Composable
    private fun mainView(viewModel: MainViewModel) {
        val pageState = remember { mutableStateOf(0) }
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom
        ) {
            when(pageState.value){
                1 -> ScheduleView(Modifier.weight(1.0f))
                2 -> ServiceHallView(Modifier.weight(1.0f))
                3 -> MinePageView(Modifier.weight(1.0f))
                else -> MainPageView(Modifier.weight(1.0f), viewModel)
            }

            BottomNavigationBar(initSelectedIndex = 0) {
                pageState.value = it
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun SchedulePagePreview(){
        ScheduleView(modifier = Modifier.fillMaxWidth().fillMaxHeight())
    }


}

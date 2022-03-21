package com.skullmind.io.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.skullmind.io.main.widget.MainBottomNavigationBar.BottomNavigationBar
import com.skullmind.io.main.widget.MainPage.MainPageView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            mainView()

        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun mainView() {
        Column(modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom) {
            MainPageView(Modifier.weight(1.0f))
            BottomNavigationBar(initSelectedIndex = 0)
        }
    }


}

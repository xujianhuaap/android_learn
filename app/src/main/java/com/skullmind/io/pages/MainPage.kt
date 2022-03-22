package com.skullmind.io.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.skullmind.io.R
import com.skullmind.io.main.MainViewModel
import com.skullmind.io.main.vo.ConfigItem
import com.skullmind.io.main.widget.MainConfigMenu.ConfigMenu
import com.skullmind.io.main.widget.MainConfigMenu.showTipDialog

object MainPage {
    @Composable
    fun MainPageView(modifier: Modifier, viewModel: MainViewModel) {
        val showTipDialogState = remember {
            mutableStateOf(
                Pair(
                    false,
                    ConfigItem("default", "", R.mipmap.ic_launcher)
                )
            )
        }

        Column(
            modifier = modifier.then(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color.LightGray)

            )
        ) {

            ConfigMenu(data = viewModel.getMenuSources()) {
                showTipDialogState.value = Pair(true, it)
            }

            showTipDialog(state = showTipDialogState)
        }

    }

}
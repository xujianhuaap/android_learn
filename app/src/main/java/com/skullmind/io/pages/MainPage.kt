package com.skullmind.io.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
        Surface(
            modifier = modifier.then(
                Modifier.fillMaxWidth()
            )
        ) {

            Column {

                ConfigMenu(data = viewModel.getMenuSources()) {
                    showTipDialogState.value = Pair(true, it)
                }

                showTipDialog(state = showTipDialogState)
            }
        }


    }

}
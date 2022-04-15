package com.skullmind.io.pages

import android.content.res.Resources
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.skullmind.io.R
import com.skullmind.io.main.MainViewModel
import com.skullmind.io.main.vo.ConfigItem
import com.skullmind.io.main.vo.NoticeVo
import com.skullmind.io.main.vo.getAdvertisements
import com.skullmind.io.main.vo.getNotices
import com.skullmind.io.main.widget.MainConfigMenu.ConfigMenu
import com.skullmind.io.main.widget.MainConfigMenu.showTipDialog
import com.skullmind.io.main.widget.MainTopAdvertisement.ViewPager
import com.skullmind.io.main.widget.MarqueeView

object MainPage {
    @Composable
    fun MainPageView(modifier: Modifier, viewModel: MainViewModel, mainNavigator: MainNavigator) {
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
                ViewPager(getAdvertisements())
                MarqueeView(
                    getNotices(),
                    0,
                    screenWidth = LocalConfiguration.current.screenWidthDp * Resources.getSystem().displayMetrics.density,
                ){
                    mainNavigator.marqueeClick(it)
                }
                ConfigMenu(data = viewModel.getMenuSources()) {
                    showTipDialogState.value = Pair(true, it)
                }

                showTipDialog(state = showTipDialogState)
            }
        }


    }

}

interface MainNavigator {
    fun marqueeClick(noticeVo:NoticeVo)
}


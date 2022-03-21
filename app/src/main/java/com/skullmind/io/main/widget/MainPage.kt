package com.skullmind.io.main.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.skullmind.io.main.menu_config.ConfigItem
import com.skullmind.io.main.menu_config.getMenuData

object MainPage {
    @Composable
    fun MainPageView(modifier: Modifier) {
        Column(
            modifier = modifier.then(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color.LightGray)

            )
        ) {

            ConfigMenu(data = getMenuData())
        }

    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ConfigMenu(data: List<ConfigItem>) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(4),
            modifier = Modifier.fillMaxWidth().background(color = Color.White),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(data.size) {
                MenuItem(logoId = data[it].resId, content = data[it].title)

            }
        }
    }

    @Composable
    fun MenuItem(@DrawableRes logoId: Int, content: String) {
        Column(modifier = Modifier.border(width = 1.dp,brush = SolidColor(Color.Black), shape = RoundedCornerShape(0,0,0,0))) {
            Image(
                painter = painterResource(id = logoId),
                contentDescription = content,
                modifier = Modifier.size(30.dp).align(Alignment.CenterHorizontally)
            )
            Text(text = content, textAlign = TextAlign.Center,
                modifier = Modifier.width(70.dp).align(Alignment.CenterHorizontally))
        }
    }
}
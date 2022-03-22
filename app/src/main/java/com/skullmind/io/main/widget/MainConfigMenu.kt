package com.skullmind.io.main.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.skullmind.io.main.vo.ConfigItem

object MainConfigMenu {
    @Composable
    fun showTipDialog(state: MutableState<Pair<Boolean, ConfigItem>>) {
        if (state.value.first) {
            AlertDialog(
                onDismissRequest = { state.value = Pair(false, state.value.second) },
                confirmButton = {
                    Button(
                        onClick = { state.value = Pair(false, state.value.second) },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.DarkGray,
                            contentColor = Color.White
                        )
                    ) {
                        Text("确认")
                    }
                },
                text = {
                    Text(
                        text = "欢迎使用南航${state.value.second.title}服务",
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                title = { Text("南航服务菜单") }
            )
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ConfigMenu(data: List<ConfigItem>, onClick: (ConfigItem) -> Unit) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(4),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .border(
                    width = 1.dp,
                    brush = SolidColor(Color.Black),
                    shape = RoundedCornerShape(0, 0, 0, 0)
                ),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            items(data.size) {
                MenuItem(logoId = data[it].resId, content = data[it].title) {
                    onClick(data[it])
                }

            }
        }
    }

    @Composable
    private fun MenuItem(@DrawableRes logoId: Int, content: String, onClick: () -> Unit) {
        Column(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    brush = SolidColor(Color.Black),
                    shape = RoundedCornerShape(0, 0, 0, 0)
                )
                .clickable { onClick() }
        ) {
            Image(
                painter = painterResource(id = logoId),
                contentDescription = content,
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = content, textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(70.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}
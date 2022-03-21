package com.skullmind.io.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skullmind.io.R


private const val ACTIVITY_LABEL_HOME = "首页"
private const val ACTIVITY_LABEL_SCHEDULE = "行程"
private const val ACTIVITY_LABEL_SERVICE_HALL = "服务大厅"
private const val ACTIVITY_LABEL_MINE = "我"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            BottomNavigationBar(selectedIndex = 0)
        }
    }

    @Composable
    fun BottomNavigationBar(selectedIndex: Int) {
        val selectedIndexState  = remember {
            mutableStateOf(selectedIndex)
        }
        val selectHandler:(Int) -> Unit = {
            selectedIndexState.value = it

        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            TaleItem(
                content = ACTIVITY_LABEL_HOME, logoId = R.mipmap.launcher_footer_icon1_normol,
                if (selectedIndexState.value == 0) Color(0xFF226CC9) else Color.Black,
                selectHandler = selectHandler
            )
            TaleItem(
                content = ACTIVITY_LABEL_SCHEDULE, logoId = R.mipmap.launcher_footer_icon2_normol,
                if (selectedIndexState.value == 1) Color(0xFF226CC9) else Color.Black,
                selectHandler = selectHandler
            )
            TaleItem(
                content = ACTIVITY_LABEL_SERVICE_HALL, logoId = R.mipmap.launcher_footer_icon4_normol,
                if (selectedIndexState.value == 2) Color(0xFF226CC9) else Color.Black,
                selectHandler = selectHandler
            )
            TaleItem(
                content = ACTIVITY_LABEL_MINE, R.mipmap.launcher_footer_icon5_normol,
                if (selectedIndexState.value == 3) Color(0xFF226CC9) else Color.Black,
                selectHandler = selectHandler
            )
        }
    }

    @Composable
    fun TaleItem(
        content: String,
        @DrawableRes logoId: Int,
        tintColor: Color,
        selectHandler: ((Int) -> Unit)?
    ) {
        Column(modifier = Modifier
            .padding(vertical = 5.dp)
            .clickable {
                selectHandler?.invoke(convertLabelToIndex(content))

            }) {
            Modifier.weight(1.0f)
            Image(
                painter = painterResource(id = logoId),
                contentDescription = content,
                colorFilter = ColorFilter.tint(tintColor),
                modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.CenterHorizontally),
            )
            Text(
                text = content,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                color = tintColor,
                modifier = Modifier
                    .width(40.dp)
                    .align(Alignment.CenterHorizontally)
            )

        }
    }

    private fun convertLabelToIndex(label: String): Int = when (label) {
        ACTIVITY_LABEL_HOME -> 0
        ACTIVITY_LABEL_SCHEDULE -> 1
        ACTIVITY_LABEL_SERVICE_HALL -> 2
        ACTIVITY_LABEL_MINE -> 3
        else -> 0
    }

    @Preview(showBackground = true)
    @Composable
    fun BottomNavigationBarPreview() {
        BottomNavigationBar(0)
    }

    @Preview(showBackground = true)
    @Composable
    fun TableItemPreview() {
        TaleItem(content = ACTIVITY_LABEL_HOME, logoId = R.mipmap.launcher_footer_icon1_normol, Color.Blue, null)
    }
}

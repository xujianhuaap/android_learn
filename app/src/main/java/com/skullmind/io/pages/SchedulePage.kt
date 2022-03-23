package com.skullmind.io.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skullmind.io.R
import com.skullmind.io.main.vo.FlightInfo
import java.util.*

object SchedulePage {
    @Composable
    fun ScheduleView(modifier: Modifier) {
        Column(
            modifier = modifier
                .then(Modifier.background(color = Color(0xFE2B435A)))
                .fillMaxWidth()
        ) {
            ScheduleItemView(modifier = modifier)
        }

    }


    @Composable
    fun ScheduleItemView(modifier: Modifier) {
        val flightInfo = FlightInfo("广州", "北京", "CZ9834", Date(), Date())
        DateView("2022年03月12日")
        LinkLineView()
        FlightItemView(flightInfo = flightInfo)

    }

    @Composable
    private fun FlightItemView(flightInfo: FlightInfo) {
        Surface(
            shape = RoundedCornerShape(20, 20, 20, 20),
            color = Color.White
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                flightTitleView(flightInfo.depCity, flightInfo.arrCity, flightInfo.flightNo)
                flightMainInfoView()
            }
        }

    }

    @Composable
    private fun flightMainInfoView() {
        Box(modifier = Modifier.fillMaxWidth().background(color = Color(0xFF4581B2))) {
            Text(text = "12334")
        }
    }

    @Composable
    private fun flightTitleView(depCity: String, arrCity: String, flightNO: String) {
        Row(modifier = Modifier.padding(horizontal = 10.dp),verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.book_ic_fly),
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp, 30.dp)
                    .padding(5.dp)
            )
            Text(text = "$depCity-$arrCity", fontSize = 16.sp)
            Text(text = flightNO,fontSize=16.sp, textAlign = TextAlign.End, modifier = Modifier.weight(1.0f))
        }
    }

    @Composable
    private fun LinkLineView() {
        Text(
            modifier = Modifier
                .size(21.dp, 25.dp)
                .padding(start = 20.dp)
                .background(color = Color.White),

            color = Color.White,
            text = ""
        )
    }

    @Composable
    private fun DateView(date: String) {
        Surface(
            shape = RoundedCornerShape(15, 15, 15, 15),
            color = Color(0xFF587C8C)
        ) {
            Column() {
                Text(
                    text = date,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
                )
            }
        }
    }
}
package com.skullmind.io.main.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.skullmind.io.R
import com.skullmind.io.main.vo.FlightInfo
import java.text.SimpleDateFormat
import java.util.*

object ScheduleItemView {
    @Composable
    fun ScheduleItemView(flightInfo: FlightInfo) {

        DateView(formatDate(flightInfo.depDate))
        LinkLineView()
        FlightInfoView(flightInfo = flightInfo)
        DividerView()

    }

    @Composable
    private fun DividerView() {
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)) {}
    }

    @Composable
    private fun TicketInfoView(flightInfo: FlightInfo) {
        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp)) {
            val (ticketNo, bookNo) = createRefs()
            Text("客票号：${flightInfo.ticketNO}", Modifier.constrainAs(ticketNo) {
                top.linkTo(parent.top, 5.dp)
                start.linkTo(parent.start, 20.dp)

            })

            Text("预订编码：${flightInfo.bookNo}", Modifier.constrainAs(bookNo) {
                top.linkTo(parent.top, 5.dp)
                end.linkTo(parent.end, 20.dp)

            })
        }
    }

    @Composable
    private fun FlightInfoView(flightInfo: FlightInfo) {
        Surface(
            shape = AbsoluteRoundedCornerShape(CornerSize(12.dp)),
            color = Color.White
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                flightTitleView(flightInfo.depCity, flightInfo.arrCity, flightInfo.flightNo)
                flightMainInfoView(flightInfo = flightInfo)
                TicketInfoView(flightInfo = flightInfo)
                FlightOptView()
            }
        }

    }

    @Composable
    private fun FlightOptView() {
        Row {
            FlightOptItemView(R.mipmap.main_icon_home_upgrade_cabin, "升舱", Modifier.weight(0.45f))
            FlightOptItemView(R.mipmap.main_icon_home_upgrade_cabin, "升舱", Modifier.weight(0.35f))
            FlightOptItemView(R.mipmap.main_icon_home_upgrade_cabin, "升舱", Modifier.weight(0.2f))
        }
    }

    @Composable
    private fun FlightOptItemView(resID: Int, title: String, modifier: Modifier) {
        ConstraintLayout(modifier = modifier.then(Modifier.height(40.dp))) {
            val (logo, topLine, rightLine) = createRefs()

            Row(modifier = Modifier.constrainAs(logo) {
                centerHorizontallyTo(parent)
                centerVerticallyTo(parent)
            }, verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = resID),
                    contentDescription = title,
                    Modifier
                        .size(20.dp, 20.dp)

                )

                Text(
                    text = title,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                )
            }

            Text(
                text = "",
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.8.dp)
                    .background(color = Color.LightGray)
                    .constrainAs(topLine) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Text(
                text = "",
                fontSize = 12.sp,
                modifier = Modifier
                    .width(0.8.dp)
                    .fillMaxHeight()
                    .background(color = Color.LightGray)
                    .constrainAs(rightLine) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
            )

        }
    }

    @Composable
    private fun flightMainInfoView(flightInfo: FlightInfo) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF4581B2)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            flightDepCardInfo(flightInfo, Modifier.weight(1.0f))
            Column(Modifier.weight(0.2f), verticalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.book_ic_fly),
                    contentDescription = "", colorFilter = ColorFilter.tint(color = Color.White)
                )
                Text("计划", color = Color.White, fontSize = 12.sp,modifier = Modifier.padding(vertical = 5.dp))
            }
            flightArrCardInfo(flightInfo, Modifier.weight(1.0f))
        }

    }

    @Composable
    private fun flightDepCardInfo(flightInfo: FlightInfo, modifier: Modifier) {
        ConstraintLayout(modifier = modifier) {
            val (city, dateLabel, date, planDateLabel, planDate) = createRefs()
            Text(
                text = flightInfo.depPort,
                color = Color.White,
                modifier = Modifier.constrainAs(city) {

                    top.linkTo(parent.top, margin = 20.dp)
                    start.linkTo(parent.start, margin = 20.dp)
                })
            Text(
                text = "预计起飞当地",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.constrainAs(dateLabel) {
                    top.linkTo(city.bottom, margin = 10.dp)
                    start.linkTo(city.start)
                })
            Text(
                text = formatTime(flightInfo.depDate),
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.constrainAs(date) {
                    baseline.linkTo(dateLabel.baseline, margin = (-5).dp)
                    start.linkTo(dateLabel.end, 5.dp)
                })

            Text(
                text = "计划当地",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.constrainAs(planDateLabel) {
                    top.linkTo(dateLabel.bottom, margin = 10.dp)
                    start.linkTo(city.start)
                })
            Text(
                text = formatFullDate(flightInfo.depDate),
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.constrainAs(planDate) {
                    top.linkTo(dateLabel.bottom, margin = 10.dp)
                    bottom.linkTo(parent.bottom, margin = 20.dp)
                    start.linkTo(planDateLabel.end, 5.dp)
                })
        }
    }

    @Composable
    private fun flightArrCardInfo(flightInfo: FlightInfo, modifier: Modifier) {
        ConstraintLayout(
            modifier = modifier
        ) {
            val (city, dateLabel, date, planDateLabel, planDate) = createRefs()
            Text(
                text = flightInfo.arrPort,
                color = Color.White,
                modifier = Modifier.constrainAs(city) {

                    top.linkTo(parent.top, margin = 20.dp)
                    end.linkTo(parent.end, margin = 20.dp)
                })
            Text(
                text = "预计到达当地",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.constrainAs(dateLabel) {
                    top.linkTo(city.bottom, margin = 10.dp)
                    end.linkTo(date.start, 5.dp)
                })
            Text(
                text = formatTime(flightInfo.arrDate),
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.constrainAs(date) {
                    baseline.linkTo(dateLabel.baseline, margin = (-5).dp)
                    end.linkTo(city.end)
                })

            Text(
                text = "计划当地",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.constrainAs(planDateLabel) {
                    top.linkTo(dateLabel.bottom, margin = 10.dp)
                    end.linkTo(planDate.start, margin = 5.dp)
                })
            Text(
                text = formatFullDate(flightInfo.arrDate),
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.constrainAs(planDate) {
                    top.linkTo(dateLabel.bottom, margin = 10.dp)
                    bottom.linkTo(parent.bottom, margin = 20.dp)
                    end.linkTo(city.end)
                })
        }
    }

    @Composable
    private fun flightTitleView(depCity: String, arrCity: String, flightNO: String) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp,vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.book_ic_fly),
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp, 30.dp)
                    .padding(5.dp)
            )
            Text(text = "$depCity-$arrCity", fontSize = 16.sp)
            Text(
                text = flightNO,
                fontSize = 16.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1.0f)
            )
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

    private fun formatDate(date: Date): String = SimpleDateFormat("yyyy年MM月dd日").format(date)

    private fun formatTime(date: Date): String = SimpleDateFormat("HH:mm").format(date)

    private fun formatFullDate(date: Date): String = SimpleDateFormat("MM月dd日 HH:mm").format(date)
}
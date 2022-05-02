package com.skullmind.io.main.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.skullmind.io.R
import com.skullmind.io.main.view_model.Event
import com.skullmind.io.main.view_model.ServiceHallVM
import com.skullmind.io.main.vo.ConfigItem
import com.skullmind.io.theme.AppTheme

object ServiceHallPage {
    @Composable
    fun ServiceHallView(modifier: Modifier, viewModel: ServiceHallVM) {
        val viewState by viewModel.viewState.collectAsState()
        val lifecycleOwner = LocalLifecycleOwner.current.lifecycle
        DisposableEffect(key1 = Unit) {
            val observer = object : DefaultLifecycleObserver {
                override fun onStart(owner: LifecycleOwner) {
                    super.onStart(owner)
                    viewModel.reduce(Event.Start)
                }

                override fun onPause(owner: LifecycleOwner) {
                    super.onPause(owner)
                    viewModel.reduce(Event.Pause)
                }
            }
            lifecycleOwner.addObserver(observer)
            onDispose {
                viewModel.reduce(Event.Dispose)
            }
        }
        Column(

            modifier = modifier.then(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.secondaryVariant)
                    .padding(horizontal = 5.dp)

            )
        ) {
            SearchBox(viewState.searchHint)
            PrimaryServiceView(viewState.primaryServiceList)
        }
    }

    @Composable
    private fun PrimaryServiceView(datas: List<ConfigItem>) {
        ConstraintLayout(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        ) {
            val (labelRef, editButtonRef, logoRef, serviceListRef) = createRefs()
            Text(
                text = "我的服务",
                color = AppTheme.extraColors.onTextPrimaryColor,
                modifier = Modifier.constrainAs(labelRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
            )
            Text(modifier = Modifier
                .constrainAs(editButtonRef) {
                    end.linkTo(logoRef.start)
                    top.linkTo(labelRef.top)
                    bottom.linkTo(labelRef.bottom)
                }
                .background(Color.Transparent),
                text = "编辑",
                color = AppTheme.extraColors.onTextPrimaryColor

            )

            Image(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "",
                modifier = Modifier.constrainAs(logoRef) {
                    end.linkTo(parent.end)
                    top.linkTo(labelRef.top)
                    bottom.linkTo(labelRef.bottom)
                },
                colorFilter = ColorFilter.tint(Color.White)
            )

            LazyRow(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .constrainAs(serviceListRef){
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(labelRef.bottom,10.dp)
                    }
            ) {
                items(datas.size) {
                    Image(
                        painter = painterResource(id = datas[it].resId),
                        contentDescription = datas[it].title,
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                    )
                }
            }
        }
    }

    @Composable
    private fun SearchBox(hint: String) {
        var text by remember {
            mutableStateOf("")
        }

        TextField(
            value = text,
            placeholder = { Text(hint) },
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxWidth(0.85f),
            colors = TextFieldDefaults.textFieldColors(
                textColor = AppTheme.extraColors.textPrimaryColor,
                focusedLabelColor = AppTheme.extraColors.hintPrimaryColor,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = ""
                )
            },
            shape = RoundedCornerShape(10.dp)
        )
    }


}

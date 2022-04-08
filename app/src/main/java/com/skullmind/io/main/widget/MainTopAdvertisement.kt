package com.skullmind.io.main.widget

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.skullmind.io.R
import com.skullmind.io.main.vo.AdvertisementVo
import com.skullmind.io.main.vo.getDefaultAdvertisement

object MainTopAdvertisement {
    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun ViewPager(datas: List<AdvertisementVo>) {
        val clickItemState = remember {
            mutableStateOf(Pair(false, getDefaultAdvertisement()))
        }

        val pageState = rememberPagerState(
            pageCount = datas.size, initialPage = 0,
            initialPageOffset = 0f, initialOffscreenLimit = 3, infiniteLoop = true
        )

        HorizontalPager(state = pageState) {
            PagerFrame(datas, pagerState = pageState, clickItemState = clickItemState)
        }
        showAdvertisementDialog(clickItemState)
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun PagerFrame(
        datas: List<AdvertisementVo>,
        pagerState: PagerState,
        clickItemState: MutableState<Pair<Boolean, AdvertisementVo>>
    ) {
        Button(
            onClick = { clickItemState.value = Pair(true, datas[pagerState.currentPage]) },
            contentPadding = PaddingValues()
        ) {
            AdvertisementImage(datas[pagerState.currentPage])
        }

    }

    @Composable
    private fun AdvertisementImage(data: AdvertisementVo) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data.img)
                .crossfade(false)
                .diskCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentDescription = data.title,
            modifier = Modifier
                .fillMaxHeight(0.25f)
                .fillMaxWidth(),
            placeholder = painterResource(id = R.mipmap.ic_default_advertisement),
            contentScale = ContentScale.Crop

        )
    }


    @Composable
    private fun showAdvertisementDialog(clickItemState: MutableState<Pair<Boolean, AdvertisementVo>>) {
        if (clickItemState.value.first) {
            AlertDialog(
                onDismissRequest = {

                    clickItemState.value = Pair(false, clickItemState.value.second)
                },
                text = { Text(text = clickItemState.value.second.title) },
                title = { Text(text = "提示", fontSize = 16.sp) },
                confirmButton = {
                    Button(
                        onClick = {
                            clickItemState.value = Pair(false, clickItemState.value.second)
                        }, colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.DarkGray,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "确认", fontSize = 14.sp)
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()

            )
        }

    }
}
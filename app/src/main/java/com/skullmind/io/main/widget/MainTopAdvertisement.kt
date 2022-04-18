package com.skullmind.io.main.widget

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.google.accompanist.pager.*
import com.skullmind.io.R
import com.skullmind.io.main.vo.AdvertisementVo
import com.skullmind.io.main.vo.getDefaultAdvertisement
import com.skullmind.io.theme.AppTheme
import kotlinx.coroutines.delay

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

        LaunchedEffect(Unit) {
            while (true) {
                delay(1000 * 3)
                if (!clickItemState.value.first) pageState.animateScrollToPage((pageState.currentPage + 1))
            }

        }

        ConstraintLayout() {
            val (pagerRef, pagerIndicatorRef) = createRefs()
            HorizontalPager(state = pageState, modifier = Modifier.constrainAs(pagerRef) {


            }) {
                PagerFrame(datas, pagerState = pageState, clickItemState = clickItemState)
            }

            HorizontalPagerIndicator(
                pagerState = pageState,
                activeColor = MaterialTheme.colors.primary,
                inactiveColor = Color(0xFFABA5A5),
                modifier = Modifier.constrainAs(pagerIndicatorRef) {

                    bottom.linkTo(pagerRef.bottom,10.dp)
                    centerHorizontallyTo(parent)
                })
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
                .fillMaxHeight(0.3f)
                .fillMaxWidth(),
            placeholder = painterResource(id = R.mipmap.ic_default_advertisement),
            contentScale = ContentScale.Crop,
            colorFilter = AppTheme.colorFilter

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
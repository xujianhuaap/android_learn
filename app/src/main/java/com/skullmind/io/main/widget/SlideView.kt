package com.skullmind.io.main.widget

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun SlideView(modifier: Modifier,content:@Composable () -> Unit,slideContent:@Composable () -> Unit) {
    var dragX by remember {
        mutableStateOf(0f)
    }
    var isShow by remember {
        mutableStateOf(true)

    }

    ConstraintLayout(modifier = modifier.then(Modifier.fillMaxSize().pointerInput(Unit) {
        detectDragGestures(onDragStart = { dragX = 0f }, onDrag = { _, offset ->
            dragX += offset.x
            isShow = dragX > 100f
            Log.d("-->","onDrag $dragX")
        }, onDragEnd = {
            isShow = dragX > 100f
        })
    })) {
        val (slideRef,contentRef) = createRefs()
        AnimatedVisibility(visible = isShow, modifier = Modifier.constrainAs(slideRef){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        },enter = slideInHorizontally {-it},exit = slideOutHorizontally {-it}) {
            Box(modifier = Modifier.fillMaxSize().background(Color(0xA6635A5D))) {
                slideContent()
            }
        }

        Box(modifier = Modifier.constrainAs(contentRef){
        }){
            content()
        }
    }


}
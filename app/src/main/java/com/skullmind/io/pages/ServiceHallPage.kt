package com.skullmind.io.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skullmind.io.R
import com.skullmind.io.pages.ServiceHallPage.ServiceHallView
import com.skullmind.io.theme.AppTheme

object ServiceHallPage {
    @Composable
    fun ServiceHallView(modifier: Modifier) {
        Column(
            modifier = modifier.then(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp,end = 5.dp)
                    .background(MaterialTheme.colors.secondaryVariant)
            )
        ) {
            SearchBox()
        }
    }

    @Composable
    private fun SearchBox() {
        var text by remember {
            mutableStateOf("")
        }
        TextField(
            value = text,
            placeholder = { Text("机票/酒店/度假/出行指南") },
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
                    painter = painterResource(id = R.drawable.book_ic_fly),
                    contentDescription = ""
                )
            },
            shape = RoundedCornerShape(10.dp)
        )
    }


}

@Composable
@Preview(showBackground = true)
fun previewServiceHallPage() {
    ServiceHallView(Modifier.fillMaxWidth())
}
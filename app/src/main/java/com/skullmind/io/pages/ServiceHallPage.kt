package com.skullmind.io.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.skullmind.io.R
import com.skullmind.io.pages.ServiceHallPage.ServiceHallView
import com.skullmind.io.theme.AppTheme

object ServiceHallPage {
    @Composable
    fun ServiceHallView(modifier: Modifier) {
        Column() {
            SearchBox()
        }
        Text(
            "服务大厅页面",
            modifier = modifier.then(Modifier.fillMaxWidth()),
            fontSize = 40.sp,
            textAlign = TextAlign.Center
        )
    }

    @Composable
    private fun SearchBox() {
        var text by remember {
            mutableStateOf("")
        }
        TextField(
            value = text,
            label = { Text("机票/酒店/度假/出行指南") },
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                textColor = AppTheme.extraColors.textPrimaryColor,
                focusedLabelColor = AppTheme.extraColors.hintPrimaryColor
            ),
            leadingIcon = {
                Image(painter = painterResource(id = R.drawable.book_ic_fly), contentDescription = "")
            }
        )
    }


}

@Composable
@Preview
fun previewServiceHallPage() {
    ServiceHallView(Modifier.fillMaxWidth())
}
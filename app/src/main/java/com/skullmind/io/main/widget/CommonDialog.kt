package com.skullmind.io.main.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.skullmind.io.main.vo.DialogVo

@Composable
 fun <T :DialogVo>showDialog(clickItemState: MutableState<Pair<Boolean, T>>) {
    if (clickItemState.value.first) {
        AlertDialog(
            onDismissRequest = {

                clickItemState.value = Pair(false, clickItemState.value.second)
            },
            text = { Text(text = clickItemState.value.second.getTitleContent()) },
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

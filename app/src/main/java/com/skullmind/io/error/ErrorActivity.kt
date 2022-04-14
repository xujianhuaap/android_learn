package com.skullmind.io.error

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import java.io.File


fun startErrorActivity(activity: AppCompatActivity) = Intent(activity,ErrorActivity::class.java).run {
    activity.startActivity(this)
}
class ErrorActivity:AppCompatActivity (),ErrorActivityNavigator{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ErrorListView{
                this.clickErrorItem(it)
            }
        }
    }

    override fun clickErrorItem(file: File) {
        startErrorDetailActivity(this, getErrorDetailInfo(application,file))
    }

    @Composable
    private fun ErrorListView(click:(File) -> Unit) {
        val selectIndex by remember{
            mutableStateOf(0)
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(getErrorFiles(application).size) {
                ErrItem(getErrorFiles(application)[it], click)
            }
        }
    }

    @Composable
    private fun ErrItem(it: File, click: (File) -> Unit) {
        Text(
            text = it.name,
            modifier = Modifier.clickable {
                click(it)
            })
    }
}
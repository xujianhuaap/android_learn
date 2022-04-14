package com.skullmind.io.error

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.ui.Modifier

fun startErrorDetailActivity(activity: AppCompatActivity, errorInfo: String) =
    Intent(activity, ErrorDetailActivity::class.java).run {
        putExtra("extra_key_error_detail", errorInfo)
        activity.startActivity(this)
    }

class ErrorDetailActivity : AppCompatActivity() {
    private lateinit var errorInfo: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        errorInfo = intent.getStringExtra("extra_key_error_detail") ?: ""
        setContent {
            LazyColumn() {
                items(1){
                    Text(
                        text = errorInfo
                    )
                }

            }

        }
    }
}
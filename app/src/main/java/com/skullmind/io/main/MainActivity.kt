package com.skullmind.io.main

import android.os.Bundle
import android.widget.EditText
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.skullmind.io.R


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PreviewMessageCard()
        }
    }

    @Composable
    fun MessageCard(name: String) {
        Text(text = "Hello $name!")
    }

    @Composable
    fun LoginCard(){
        Row() {
            Box(){
                Image(painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "personal image")
                Image(painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "personal image")

            }

            Column() {
                Text(text = "LoginName")
                Text(text = "Password")
            }
        }


    }
    @Preview
    @Composable
    fun PreviewMessageCard() {
        Column() {
            LoginCard()
        }

    }
}

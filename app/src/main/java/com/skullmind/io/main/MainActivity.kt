package com.skullmind.io.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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


    @Composable
    fun HelloContent() {
        Column(modifier = Modifier.padding(16.dp)) {
            var name by remember { mutableStateOf("") }
            if (name.isNotEmpty()) {
                Text(
                    text = "Hello, $name!",
                    modifier = Modifier.padding(bottom = 8.dp),
                    style = MaterialTheme.typography.h5
                )
            }
            OutlinedTextField(
                value ="@$name",
                onValueChange = { name = it },
                label = { Text("姓名") }
            )
        }
    }

    @Preview
    @Composable
    fun PreviewMessageCard() {
      HelloContent()

    }
}

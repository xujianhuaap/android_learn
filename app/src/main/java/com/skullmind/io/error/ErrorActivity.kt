package com.skullmind.io.error

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.ui.Modifier


fun startErrorActivity(activity: AppCompatActivity) = Intent(activity,ErrorActivity::class.java).run {
    activity.startActivity(this)
}
class ErrorActivity:AppCompatActivity (){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LazyColumn(modifier = Modifier.fillMaxSize()){
                items(getErrorFiles(application).size){
                    Text(text=getErrorFiles(application = application).get(it).name)
                }
            }
        }
    }
}
package com.skullmind.io.main

import android.view.View
import android.widget.Toast
import com.skullmind.io.github.newIntentToGitHub

class MainNavigator(internal val activity: MainActivity){
    fun showToast(message:String){
        Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
    }

    fun jumpToGitHub(){
        activity.startActivity(newIntentToGitHub(activity,"JakeWharton"))
    }
}
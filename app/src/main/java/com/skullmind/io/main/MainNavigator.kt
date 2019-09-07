package com.skullmind.io.main

import android.widget.Toast

class MainNavigator(internal val activity: MainActivity){
    fun showToast(message:String){
        Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
    }
}
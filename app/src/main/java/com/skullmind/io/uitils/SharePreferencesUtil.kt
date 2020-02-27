package com.skullmind.io.uitils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharePreferencesUtil {
    var sharePreferences:SharedPreferences?=null
    private constructor(context: Context){
        sharePreferences = context!!.getSharedPreferences("android_learn", Context.MODE_PRIVATE)
    }


    companion object{
        var instance:SharePreferencesUtil ?= null
        @Synchronized
        private fun getInstance(context: Context):SharePreferencesUtil{
            if(instance == null){
                instance = SharePreferencesUtil(context)
            }
            return instance
        }

        fun init(context: Context){
            instance.context = context
        }
    }
}
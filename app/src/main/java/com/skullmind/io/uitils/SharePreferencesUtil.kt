package com.skullmind.io.uitils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

const val SHARE_PREFERENCES_KEY_APP_NAME = "sp_key_app_name"
class SharePreferencesUtil {
    var sharePreferences:SharedPreferences?=null
    private constructor(context: Context){
        sharePreferences = context!!.getSharedPreferences("android_learn", Context.MODE_PRIVATE)
    }


    companion object{
        private var instance:SharePreferencesUtil ?= null
        @Synchronized
        private fun getInstances(context: Context):SharePreferencesUtil{
            if(instance == null){
                instance = SharePreferencesUtil(context)
            }
            return instance as SharePreferencesUtil
        }

        fun init(context: Context){
            getInstances(context)
        }

        fun edit(action:SharedPreferences.Editor.()->Unit){
            instance?.sharePreferences?.edit {
                action()
            }
        }

        fun getString(key:String):String = instance?.sharePreferences?.getString(key,"")?:""

    }
}
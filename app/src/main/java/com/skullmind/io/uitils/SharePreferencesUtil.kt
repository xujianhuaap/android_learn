package com.skullmind.io.uitils

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

const val SHARE_PREFERENCES_KEY_APP_NAME = "sp_key_app_name"
const val SECURITY_SHARE_PREFERENCE_FILE_NAME = "security_sp_android_learn"
class SharePreferencesUtil {
    var sharePreferences:SharedPreferences?=null
    var securitySharedPreferences:SharedPreferences?=null
    private constructor(context: Context){
        sharePreferences = context!!.getSharedPreferences("android_learn", Context.MODE_PRIVATE)
        val masterAlia = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        securitySharedPreferences = EncryptedSharedPreferences.create(SECURITY_SHARE_PREFERENCE_FILE_NAME,
            masterAlia,context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
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

        fun editForSecurity(action: SharedPreferences.Editor.() -> Unit){
            instance?.securitySharedPreferences?.edit { action }
        }

        fun getString(key:String):String = instance?.sharePreferences?.getString(key,"")?:""

    }
}
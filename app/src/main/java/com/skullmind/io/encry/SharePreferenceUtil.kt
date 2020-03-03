package com.skullmind.io.encry

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

const val SHARE_PREFERENCE_FILE_NAME = "sp_android_learn"
const val SP_KEY_APP_NAME = "sp_key_app_name"
class SharePreferenceUtil {
    private var sharedPreferences:SharedPreferences ?= null
    private constructor(context: Context){
        val alia = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        sharedPreferences = EncryptedSharedPreferences.create(SHARE_PREFERENCE_FILE_NAME,
            alia,context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
    }
    companion object{
        private var instance:SharePreferenceUtil ?= null
        fun edit(action:SharedPreferences.Editor.()->Unit){
            instance?.sharedPreferences?.edit { action }
        }

        @Synchronized
        fun init(context: Context){
            instance = instance?:SharePreferenceUtil(context)
        }
    }
}

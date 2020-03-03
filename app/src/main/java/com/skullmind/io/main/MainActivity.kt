package com.skullmind.io.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import com.skullmind.io.R
import com.skullmind.io.encry.SP_KEY_APP_NAME
import com.skullmind.io.encry.SharePreferenceUtil


class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SharePreferenceUtil.edit { putString(SP_KEY_APP_NAME,"123") }
    }


}

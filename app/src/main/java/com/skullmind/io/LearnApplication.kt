package com.skullmind.io

import android.app.Activity
import android.app.Application
import android.content.Context
import com.skullmind.io.uitils.SharePreferencesUtil


class LearnApplication:Application(){
    override fun onCreate() {
        super.onCreate()
        SharePreferencesUtil.init(this)
    }
}
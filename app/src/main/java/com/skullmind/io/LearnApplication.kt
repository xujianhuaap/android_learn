package com.skullmind.io

import android.app.Activity
import android.app.Application
import android.content.Context
import com.skullmind.io.encry.SharePreferenceUtil
import dagger.android.*
import javax.inject.Inject

class LearnApplication:Application(),HasActivityInjector{
    @Inject
    lateinit var mActivityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>;
    @Inject
    lateinit var mContext:Context;
    override fun onCreate() {
        super.onCreate()
        SharePreferenceUtil.init(this)


    }

    override fun activityInjector(): AndroidInjector<Activity> = mActivityDispatchingAndroidInjector;
}
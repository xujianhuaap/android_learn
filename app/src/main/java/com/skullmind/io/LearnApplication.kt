package com.skullmind.io

import android.app.Activity
import android.app.Application
import dagger.android.*
import javax.inject.Inject

class LearnApplication:Application(),HasActivityInjector{
    @Inject
    lateinit var mActivityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>;
    override fun onCreate() {
        super.onCreate()


    }

    override fun activityInjector(): AndroidInjector<Activity> = mActivityDispatchingAndroidInjector;
}
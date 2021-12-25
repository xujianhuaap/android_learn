package com.skullmind.io

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import com.google.common.base.Optional
import com.skullmind.io.dagger.component.DaggerAppComponent
import com.skullmind.io.dagger.module.School
import com.skullmind.io.main.Person
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class LearnApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var mActivityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var mContext: Context

    @Inject
    lateinit var mPerson: Person

    @Inject
    lateinit var mSchool: Optional<School>

    override

    fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

        Log.d("-->", "${mSchool.get()}")


    }

    override fun activityInjector(): AndroidInjector<Activity> = mActivityDispatchingAndroidInjector
}
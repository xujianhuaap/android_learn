package com.skullmind.io.dagger.module

import androidx.databinding.DataBindingUtil
import chat.rocket.android.dagger.scope.PerActivity
import com.skullmind.io.R
import com.skullmind.io.camera.CameraModel
import com.skullmind.io.databinding.ActivityMainBinding
import com.skullmind.io.main.MainActivity
import com.skullmind.io.main.MainModel
import com.skullmind.io.main.MainNavigator
import dagger.Module
import dagger.Provides

@Module
class MainModule{
    @Provides
    @PerActivity
    fun provideNavigator(activity: MainActivity): MainNavigator =
        MainNavigator(activity)

    @Provides
    @PerActivity
    fun provideBinding(activity: MainActivity):ActivityMainBinding =
        DataBindingUtil.setContentView(activity, R.layout.activity_main)

    @Provides
    @PerActivity
    fun provideModel():MainModel = MainModel()

    @Provides
    @PerActivity
    fun provideCameraModel():CameraModel=CameraModel()
}
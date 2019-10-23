package com.skullmind.io.dagger.module;

import android.content.Context;
import androidx.lifecycle.ViewModelProviders
import chat.rocket.android.dagger.scope.PerActivity;
import com.skullmind.io.camera.Camera;
import com.skullmind.io.camera.CameraActivity
import com.skullmind.io.camera.CameraViewModel
import dagger.Module;
import dagger.Provides;

@Module
class CameraModule {
    @PerActivity
    @Provides
    fun  provideCamera(context: Context):Camera = Camera(context)

    @PerActivity
    @Provides
    fun provideModel(activity:CameraActivity)=  ViewModelProviders.of(activity).get(CameraViewModel::class.java)
}

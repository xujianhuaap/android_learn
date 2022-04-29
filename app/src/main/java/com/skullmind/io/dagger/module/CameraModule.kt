package com.skullmind.io.dagger.module;

import android.content.Context;
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import chat.rocket.android.dagger.scope.PerActivity;
import com.skullmind.io.camera.Camera;
import com.skullmind.io.camera.CameraActivity
import com.skullmind.io.camera.CameraViewModel
import com.skullmind.io.camera.SurfaceHolderCallBack
import dagger.Module;
import dagger.Provides;

@Module
class CameraModule {
    @PerActivity
    @Provides
    fun  provideCamera(context: Context):Camera = Camera(context)

    @PerActivity
    @Provides
    fun provideModel(activity:CameraActivity):ViewModel= ViewModelProvider(activity)[CameraViewModel::class.java]

    @PerActivity
    @Provides
    fun provideSurfaceHolderCallBack (activity: CameraActivity,camera:Camera) = SurfaceHolderCallBack(activity,camera)
}

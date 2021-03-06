package com.skullmind.io.dagger.module

import chat.rocket.android.dagger.scope.PerActivity
import com.skullmind.io.camera.CameraActivity
import com.skullmind.io.github.GitHubActivity
import com.skullmind.io.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder{
    @PerActivity
    @ContributesAndroidInjector(modules = [
      MainModule::class
    ])
    abstract fun bindMainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [
        GitHubModule::class
    ])
    abstract fun bindGitHubActivity(): GitHubActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [CameraModule::class])
    abstract fun bindCameraActrivity():CameraActivity
}
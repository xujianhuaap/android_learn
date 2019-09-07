package com.skullmind.io.dagger.module

import chat.rocket.android.dagger.scope.PerActivity
import com.skullmind.io.main.MainActivity
import com.skullmind.io.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder{
    @PerActivity
    @ContributesAndroidInjector(modules = [
      MainModule::class
    ])
    abstract fun bindMainActivity(): MainActivity
}
package com.skullmind.io.main

import chat.rocket.android.dagger.scope.PerActivity
import com.skullmind.io.main.MainPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainModule{
    @Provides
    @PerActivity
    fun provideNavigator(activity: MainActivity): MainNavigator= MainNavigator(activity)

    @Provides
    @PerActivity
    fun provideView(activity: MainActivity):MainView = activity


}
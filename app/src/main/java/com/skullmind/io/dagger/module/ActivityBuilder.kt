package com.skullmind.io.dagger.module

import chat.rocket.android.dagger.scope.PerActivity
import com.skullmind.io.main.MainActivity
import com.skullmind.io.person.PersonActivity
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
        PersonModule::class
    ])
    abstract fun bindPersonActivity(): PersonActivity
}
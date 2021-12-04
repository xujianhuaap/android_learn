package com.skullmind.io.dagger.module

import chat.rocket.android.dagger.scope.PerActivity
import com.skullmind.io.constraint.FlowActivity
import com.skullmind.io.dagger.module.constraint.FlowModule
import com.skullmind.io.dagger.module.main.MainModule
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
        FlowModule::class
    ])

    abstract fun bindConstraintActivity(): FlowActivity
}
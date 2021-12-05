package com.skullmind.io.dagger.module

import chat.rocket.android.dagger.scope.PerActivity
import com.skullmind.io.constraint.ConstraintBasicActivity
import com.skullmind.io.constraint.FlowActivity
import com.skullmind.io.constraint.MotionActivity
import com.skullmind.io.dagger.module.constraint.ConstraintModule
import com.skullmind.io.dagger.module.constraint.FlowModule
import com.skullmind.io.dagger.module.constraint.MotionModule
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

    @PerActivity
    @ContributesAndroidInjector(modules = [
        ConstraintModule::class
    ])

    abstract fun bindConstraintBasicActivity():ConstraintBasicActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [
        MotionModule::class
    ])
    abstract  fun bindMotionActivity():MotionActivity
}
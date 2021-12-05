package com.skullmind.io.dagger.component

import android.app.Application
import com.skullmind.io.LearnApplication
import com.skullmind.io.dagger.module.ActivityBuilder
import com.skullmind.io.dagger.module.AppModule
import com.skullmind.io.dagger.module.constraint.ConstraintModule
import com.skullmind.io.dagger.module.constraint.FlowModule
import com.skullmind.io.dagger.module.constraint.MotionModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    FlowModule::class,
    ConstraintModule::class,
    MotionModule::class,
    ActivityBuilder::class
])
interface AppComponent{
    @Singleton
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application:Application):Builder
        fun build():AppComponent
    }

    abstract fun inject( app: LearnApplication)
}
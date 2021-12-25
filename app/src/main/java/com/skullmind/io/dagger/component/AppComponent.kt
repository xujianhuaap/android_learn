package com.skullmind.io.dagger.component

import android.app.Application
import com.skullmind.io.LearnApplication
import com.skullmind.io.dagger.module.ActivityBuilder
import com.skullmind.io.dagger.module.AppModule
import com.skullmind.io.main.Student
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ActivityBuilder::class
])
interface AppComponent{
    @Singleton
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application:Application):Builder
        @BindsInstance
        fun person(student: Student):Builder
        fun build():AppComponent
    }

    abstract fun inject( app: LearnApplication)
}
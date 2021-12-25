package com.skullmind.io.dagger.module

import android.app.Application
import android.content.Context
import com.skullmind.io.main.Person
import com.skullmind.io.main.Student
import dagger.*
import javax.inject.Singleton

@Module
class AppModule{
    @Provides
    @Singleton
    fun provideContext(app:Application): Context = app

    @Provides
    @Singleton
    fun provideSchool() = School()
}
@Module
abstract class AppConfigModule{
   @Binds  abstract fun providePerson(student: Student):Person
   @BindsOptionalOf abstract fun optionalSchool():School
}


class School {}
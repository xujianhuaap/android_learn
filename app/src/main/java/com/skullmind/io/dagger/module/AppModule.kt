package com.skullmind.io.dagger.module

import android.app.Application
import android.content.Context
import com.skullmind.io.main.Person
import com.skullmind.io.main.Student
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

@Module
class AppModule{
    @Provides
    @Singleton
    fun provideContext(app:Application): Context = app


}
@Module
abstract class AppConfigModule{
   @Binds  abstract fun providePerson(student: Student):Person
}

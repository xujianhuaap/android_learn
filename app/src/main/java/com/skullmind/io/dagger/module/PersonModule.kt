package com.skullmind.io.dagger.module

import androidx.databinding.DataBindingUtil
import chat.rocket.android.dagger.scope.PerActivity
import com.skullmind.io.R
import com.skullmind.io.databinding.ActivityMainBinding
import com.skullmind.io.databinding.ActivityPersonBinding
import com.skullmind.io.person.PersonActivity
import com.skullmind.io.person.PersonModel
import dagger.Module
import dagger.Provides

@Module
class PersonModule{
    @Provides
    @PerActivity
    fun provideBinding(activity: PersonActivity):ActivityPersonBinding =
        DataBindingUtil.setContentView(activity, R.layout.activity_person)

    @Provides
    @PerActivity
    fun provideModel():PersonModel = PersonModel()
}
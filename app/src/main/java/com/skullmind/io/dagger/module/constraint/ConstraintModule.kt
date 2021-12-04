package com.skullmind.io.dagger.module.constraint

import androidx.databinding.DataBindingUtil
import chat.rocket.android.dagger.scope.PerActivity
import com.skullmind.io.R
import com.skullmind.io.constraint.ConstraintBasicActivity
import com.skullmind.io.databinding.ActivityConstraintBasicBinding
import dagger.Module
import dagger.Provides

@Module
class ConstraintModule {
    @Provides
    @PerActivity
    fun provideBinding(activity:ConstraintBasicActivity):ActivityConstraintBasicBinding
    = DataBindingUtil.setContentView(activity, R.layout.activity_constraint_basic)
}
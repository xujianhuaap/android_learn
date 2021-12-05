package com.skullmind.io.dagger.module.constraint

import androidx.databinding.DataBindingUtil
import chat.rocket.android.dagger.scope.PerActivity
import com.skullmind.io.R
import com.skullmind.io.constraint.MotionActivity
import com.skullmind.io.databinding.ActivityMotionBinding
import dagger.Module
import dagger.Provides

@Module
class MotionModule {
    @Provides
    @PerActivity
    fun provideBinding(activity: MotionActivity): ActivityMotionBinding =
        DataBindingUtil.setContentView(activity, R.layout.activity_motion)
}
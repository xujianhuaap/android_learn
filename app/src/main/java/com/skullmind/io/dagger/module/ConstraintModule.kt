package com.skullmind.io.dagger.module

import androidx.databinding.DataBindingUtil
import chat.rocket.android.dagger.scope.PerActivity
import com.skullmind.io.R
import com.skullmind.io.constraint.FlowActivity
import com.skullmind.io.databinding.ActivityFlowBinding
import dagger.Module
import dagger.Provides

@Module
class ConstraintModule {
    @PerActivity
    @Provides
    fun provideBinding(activity: FlowActivity): ActivityFlowBinding = DataBindingUtil.setContentView(activity, R.layout.activity_flow)
}
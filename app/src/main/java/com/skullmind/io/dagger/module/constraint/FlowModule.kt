package com.skullmind.io.dagger.module.constraint

import androidx.databinding.DataBindingUtil
import chat.rocket.android.dagger.scope.PerActivity
import com.skullmind.io.R
import com.skullmind.io.constraint.FlowActivity
import com.skullmind.io.databinding.ActivityFlowBinding
import dagger.Module
import dagger.Provides

@Module
class FlowModule {
    @PerActivity
    @Provides
    fun provideBinding(activity: FlowActivity): ActivityFlowBinding = DataBindingUtil.setContentView(activity, R.layout.activity_flow)
}
package com.skullmind.io.dagger.module

import androidx.databinding.DataBindingUtil
import chat.rocket.android.dagger.scope.PerActivity
import com.skullmind.io.R
import com.skullmind.io.databinding.ActivityGithubBinding
import com.skullmind.io.github.GitHubActivity
import com.skullmind.io.github.GitHubNavigator
import com.skullmind.io.github.adpater.FollowersAdapter
import dagger.Module
import dagger.Provides

@Module
class GitHubModule {
    @Provides
    @PerActivity
    fun provideBinding(activity: GitHubActivity):ActivityGithubBinding =
        DataBindingUtil.setContentView(activity, R.layout.activity_github)

    @Provides
    @PerActivity
    fun provideNavigator(activity: GitHubActivity) = GitHubNavigator(activity)

    @Provides
    @PerActivity
    fun provideAdapter() = FollowersAdapter()
}
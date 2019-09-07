package com.skullmind.io.main

import com.skullmind.io.data.GitHubService
import com.skullmind.io.retrofit.Net
import javax.inject.Inject

class MainPresenter @Inject constructor(private val navigator: MainNavigator,private val view:MainView){
    fun showMessage(message:String)=navigator.showToast(message)

    fun requestGithubService(callBack:retrofit2.Callback<GitHub>){
        Net.createService(GitHubService::class.java)
            .listGitHub()
            .enqueue(callBack)
    }

    fun refreshContentView(content:String?){
        view.refreshContentView(content)
    }
}
package com.skullmind.io.main

import android.view.View
import com.skullmind.io.databinding.ActivityMainBinding
import javax.inject.Inject

class MainPresenter @Inject constructor(private val navigator: MainNavigator,
                                        private val binding:ActivityMainBinding){
    fun initData(){
        binding.mainVM = MainViewModel("click me")
        binding.presenter = this
    }

    fun showMessage(message:String)=navigator.showToast(message)

    fun clickTitle(view: View){
        var cnt = binding?.mainVM?.titleCnt?:0
        binding?.mainVM?.titleCnt = ++cnt

    }

    fun clickToJumpGithub(view: View){
        navigator.jumpToGitHub()
    }
}
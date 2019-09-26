package com.skullmind.io.main

import android.view.View
import com.skullmind.io.camera.CameraViewModel
import com.skullmind.io.databinding.ActivityMainBinding
import javax.inject.Inject

class MainVM @Inject constructor(private val navigator: MainNavigator,
                                 private val binding:ActivityMainBinding){
    fun initData(){
        binding.mainVM = MainModel("click me")
        binding.mainVM!!.openCammara = CameraViewModel(true)
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
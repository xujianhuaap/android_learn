package com.skullmind.io.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.skullmind.io.camera.CameraModel
import com.skullmind.io.databinding.ActivityMainBinding
import com.skullmind.io.github.newIntentToGitHub
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity(){


    @Inject
    lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var vm:MainVM
    @Inject
    lateinit var model: MainModel

    @Inject
    lateinit var cameraModel:CameraModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        initData()
        binding.tvJump.setOnClickListener {
            jumpToGitHub(model.userName)
        }
    }

    private fun initData() {
        binding.vm = this.vm
        binding.model = this.model
        binding.cameraModel = this.cameraModel
        binding.vm!!.initData()
    }



    fun showToast(message:String){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }

    fun jumpToGitHub(userName:String){
        startActivity(newIntentToGitHub(this,userName))
    }

}

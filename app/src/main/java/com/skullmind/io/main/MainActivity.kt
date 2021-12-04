package com.skullmind.io.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skullmind.io.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var vm: MainVM

    @Inject
    lateinit var model: MainModel


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        initData()


    }

    private fun initData() {
        binding.vm = this.vm
        binding.model = this.model
        binding.vm!!.initData()
    }


}

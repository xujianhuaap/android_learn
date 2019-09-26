package com.skullmind.io.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity(){


    @Inject
    lateinit var vm: MainVM


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        initData()
    }

    private fun initData() {
        vm.initData()
    }

}

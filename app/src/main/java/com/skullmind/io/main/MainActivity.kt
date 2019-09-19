package com.skullmind.io.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.skullmind.io.R
import com.skullmind.io.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity(){

    @Inject
    lateinit var binding:ActivityMainBinding

    @Inject
    lateinit var presenter: MainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding.mainVM = MainViewModel("click me")
        binding.presenter = presenter
    }

}

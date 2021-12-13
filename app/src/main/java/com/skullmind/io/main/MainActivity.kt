package com.skullmind.io.main

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.skullmind.io.R
import com.skullmind.io.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity(),MainNavigation {


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
    }

    override fun showActivityMessage() {
        Toast.makeText(this,MainActivity::class.java.name,Toast.LENGTH_LONG).show()
    }
    override fun showPopWindow() {

       PopupWindow(this).apply {
            contentView = View.inflate(this@MainActivity, R.layout.window_pop_up,null)
            isOutsideTouchable= true
           val offsetY = binding.tvContent3.measuredHeight + resources.getDimensionPixelOffset(R.dimen.dimen_50_dp)
           showAsDropDown(binding.tvContent3,0,-offsetY,Gravity.BOTTOM)
        }


    }

}

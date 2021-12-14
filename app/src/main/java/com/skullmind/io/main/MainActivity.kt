package com.skullmind.io.main

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.skullmind.io.R
import com.skullmind.io.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainNavigation {


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
        Toast.makeText(this, MainActivity::class.java.name, Toast.LENGTH_LONG).show()
    }

    override fun showPopWindow() {
        val contentView = View.inflate(this@MainActivity, R.layout.window_pop_up, null).apply {
            measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        }
        PopupWindow(contentView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            this.animationStyle = R.style.BasePopWindowAnim
            this.elevation = 0f
            this.isOutsideTouchable = true
            val offsetY = binding.tvContent3.measuredHeight+contentView.measuredHeight
            this.showAsDropDown(binding.tvContent3, 0, -offsetY, Gravity.CENTER)
        }


    }

}

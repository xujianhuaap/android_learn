package com.skullmind.io.constraint

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skullmind.io.databinding.ActivityFlowBinding
import dagger.android.AndroidInjection
import javax.inject.Inject


fun toFlowActivity(activity:AppCompatActivity){
    activity.startActivity(Intent(activity,FlowActivity::class.java))
}

class FlowActivity:AppCompatActivity() {
    @Inject
    lateinit var binding: ActivityFlowBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

    }
}
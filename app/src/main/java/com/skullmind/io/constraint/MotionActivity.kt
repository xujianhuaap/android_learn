package com.skullmind.io.constraint

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skullmind.io.databinding.ActivityMotionBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

fun toMotionActivity(activity: AppCompatActivity){
    activity.startActivity(Intent(activity,MotionActivity::class.java))
}
class MotionActivity:AppCompatActivity() {
    @Inject
    lateinit var binding:ActivityMotionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}
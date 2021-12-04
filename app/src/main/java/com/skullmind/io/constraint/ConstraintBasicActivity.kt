package com.skullmind.io.constraint

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skullmind.io.databinding.ActivityConstraintBasicBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

fun toConstraintBasicActivity(activity: AppCompatActivity){
    activity.startActivity(Intent(activity,ConstraintBasicActivity::class.java))
}
class ConstraintBasicActivity:AppCompatActivity() {
    @Inject
    lateinit var binding:ActivityConstraintBasicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}
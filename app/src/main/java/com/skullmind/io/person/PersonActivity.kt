package com.skullmind.io.person

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skullmind.io.databinding.ActivityPersonBinding
import com.skullmind.io.main.Student
import dagger.android.AndroidInjection
import javax.inject.Inject

fun startPersonActivity(activity: AppCompatActivity){
    Intent(activity,PersonActivity::class.java).run {
        activity.startActivity(this)
    }
}
class PersonActivity : AppCompatActivity() {


    @Inject
    lateinit var binding: ActivityPersonBinding

    @Inject
    lateinit var vm: PersonVm

    @Inject
    lateinit var model: PersonModel

    @Inject
    lateinit var student: Student

    @Inject
    lateinit var studentAgain: Student
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        initData()


    }

    private fun initData() {
        binding.vm = this.vm
        binding.model = this.model
    }


}

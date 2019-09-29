package com.skullmind.io.github

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.skullmind.io.databinding.ActivityGithubBinding
import com.skullmind.io.github.adpater.FollowersAdapter
import dagger.android.AndroidInjection
import javax.inject.Inject

val EXTRA_USER_NAME = "extra_user_name"

fun newIntentToGitHub(context: AppCompatActivity, userName:String): Intent {
    val intent:Intent = Intent(context,GitHubActivity::class.java)
    intent.putExtra(EXTRA_USER_NAME,userName)
    return intent
}

class GitHubActivity:AppCompatActivity(){
    @Inject
    lateinit var vm: GitHubVM
    @Inject
    lateinit var binding:ActivityGithubBinding

    @Inject
    lateinit var adpater:FollowersAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        initData()
        initView()
        vm.updateFollowers()
    }

    private fun initView() {
        binding.recyleFollowers.layoutManager = LinearLayoutManager(this)
        binding.recyleFollowers.adapter = this.adpater
    }


    private fun initData() {
        val intent: Intent = getIntent()
        var userName = intent.getStringExtra(EXTRA_USER_NAME)
        binding.model = GitHubModel(userName)
        vm.init(userName, binding.model!!)
    }


}
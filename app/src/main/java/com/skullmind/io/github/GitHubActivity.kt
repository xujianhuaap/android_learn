package com.skullmind.io.github

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    lateinit var presenter: GitHubPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        initData()
        presenter.updateFollowers()
    }


    private fun initData() {
        val intent: Intent = getIntent()
        var userName = intent.getStringExtra(EXTRA_USER_NAME)
        initPresenter(userName)
    }

    private fun initPresenter(userName: String) {
        presenter.init(userName)
    }

}
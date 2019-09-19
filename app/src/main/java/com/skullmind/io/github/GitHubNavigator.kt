package com.skullmind.io.github

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skullmind.io.github.bean.User

class GitHubNavigator(internal val activity: GitHubActivity){
    fun initRecylerViewLayoutManager(view:RecyclerView){
        view.layoutManager = LinearLayoutManager(activity)
    }

    fun showToast(message:String){
        Toast.makeText(activity,message, Toast.LENGTH_LONG).show();
    }

    fun handleItemClick(user: User){
        showToast(user.login?:"unknown user")
    }
}
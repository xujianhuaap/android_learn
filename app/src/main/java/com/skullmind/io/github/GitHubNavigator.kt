package com.skullmind.io.github

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GitHubNavigator(internal val activity: GitHubActivity){
    fun initRecylerViewLayoutManager(view:RecyclerView){
        view.layoutManager = LinearLayoutManager(activity)
    }
}
package com.skullmind.io.github

import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.recyclerview.widget.RecyclerView
import com.skullmind.io.github.adpater.FollowersAdapter
import com.skullmind.io.github.bean.User


@BindingAdapter("data")
fun setAdapter(view:RecyclerView,users:List<User>){
    val adpter = view.adapter
    if(adpter is FollowersAdapter){
        adpter.setRepoList(users)
    }
}
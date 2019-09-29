package com.skullmind.io.github

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.skullmind.io.BR
import com.skullmind.io.github.bean.GitHub
import com.skullmind.io.github.bean.User

data class GitHubModel(val userName:String):BaseObservable(){
    @get:Bindable
    var users:MutableList<User> = ArrayList()

    fun  updateUsers( users:List<User>,isSelf:Boolean){
        if(users.isNotEmpty()&& !isSelf){
            this.users .clear()
            this.users.addAll(users)
        }
        notifyPropertyChanged(BR.users)
    }
}
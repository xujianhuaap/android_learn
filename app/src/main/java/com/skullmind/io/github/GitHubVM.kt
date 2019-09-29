package com.skullmind.io.github

import com.skullmind.io.data.GitHubService
import com.skullmind.io.databinding.ActivityGithubBinding
import com.skullmind.io.github.adpater.FollowersAdapter
import com.skullmind.io.github.bean.User
import com.skullmind.io.retrofit.Net
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class  GitHubVM @Inject constructor(val navigator: GitHubNavigator){
    var userName:String = ""
    var model:GitHubModel? = null

    fun init(userName: String,model: GitHubModel){
        this.userName = userName
        this.model = model
    }
    fun updateFollowers():Unit{
        reqFollowers {
            model?.updateUsers(it!!,false)
        }
    }
    fun reqFollowers(callback: (List<User>?)-> Unit){
        Net.createService(GitHubService::class.java).reqFollowers(userName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object:Subscriber<List<User>>(){
            override fun onNext(t: List<User>?) {
                callback(t)
            }

            override fun onCompleted() {

            }

            override fun onError(e: Throwable?) {

            }
        })
    }
}
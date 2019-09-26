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

class  GitHubVM @Inject constructor(val binding: ActivityGithubBinding,
                                    val navigator: GitHubNavigator,
                                    val adapter: FollowersAdapter){
    var userName:String = ""

    fun init(userName: String){
        initData(userName)
        initView()
    }
    private fun initData(userName: String){
       this.userName = userName
        val gitHubViewModel = GitHubModel(userName)
         binding.vm = gitHubViewModel
    }
    private fun initView(){
        navigator.initRecylerViewLayoutManager(binding.recyleFollowers)
        binding.recyleFollowers.adapter = adapter

    }
    fun updateFollowers():Unit{
        reqFollowers {
            adapter?.setRepoList(it?: emptyList())
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
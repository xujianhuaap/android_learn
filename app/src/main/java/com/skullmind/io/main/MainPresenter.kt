package com.skullmind.io.main

import com.skullmind.io.data.GitHubService
import com.skullmind.io.data.bean.GitHub
import com.skullmind.io.retrofit.Net
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter @Inject constructor(private val navigator: MainNavigator,private val view:MainView){
    fun showMessage(message:String)=navigator.showToast(message)

    /**
     * 函数式编程
     */
    fun requestGithubService(callBack:(GitHub?)->Unit){
        val observable = Net.createService(GitHubService::class.java)
            .listGitHub()
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object:Subscriber<GitHub>(){
                override fun onNext(t: GitHub?) {
                    callBack(t)
                }

                override fun onCompleted() {
                }

                override fun onError(e: Throwable?) {
                }
            })

    }

    fun refreshContentView(content:String?){
        view.refreshContentView(content)
    }
}
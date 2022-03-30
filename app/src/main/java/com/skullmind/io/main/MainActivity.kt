package com.skullmind.io.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.skullmind.io.Net
import com.skullmind.io.R
import com.skullmind.io.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        super.onStart()
        val info = Net.createService(User::class.java).getInfo("defunkt").subscribeOn(Schedulers.io())
        val repos = Net.createService(User::class.java).getRepos("defunkt").subscribeOn(Schedulers.io())
        fullInfo(info,repos).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(MyObserver())
    }

    private fun fullInfo(info: Observable<JsonObject>,repo: Observable<JsonObject>) =
        Observable.zip(info,repo,this::zipOperator,true)


    private fun zipOperator(info:JsonObject,repo:JsonObject):JsonObject = JsonObject().apply {
         this.add("info",info)
         this.add("repo",repo)
    }

}

class MyObserver : Observer<JsonObject> {
    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: JsonObject) {
        Log.d("-->", "MyObserver:success ->" + t)
    }

    override fun onError(e: Throwable) {

    }

    override fun onComplete() {
        Log.d("-->", "MyObserver:onComplete ->" + Thread.currentThread().name)
    }
}

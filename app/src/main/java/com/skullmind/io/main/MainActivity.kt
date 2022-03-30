package com.skullmind.io.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.skullmind.io.Net
import com.skullmind.io.R
import com.skullmind.io.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Net.createService(User::class.java).getInfo("defunkt").observeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(MyObserver())
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

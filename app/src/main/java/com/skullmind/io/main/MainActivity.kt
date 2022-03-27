package com.skullmind.io.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skullmind.io.R
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Observable.create(ObservableOnSubscribe<String> {

        }).subscribeOn(Schedulers.io()).subscribe()

    }


}

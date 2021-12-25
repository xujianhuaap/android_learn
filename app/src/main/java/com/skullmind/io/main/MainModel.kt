package com.skullmind.io.main

import android.util.Log
import androidx.databinding.BaseObservable
import dagger.Reusable
import javax.inject.Inject
import javax.inject.Singleton


class MainModel @Inject constructor() : BaseObservable() {

}

interface Person
@Reusable
class Student @Inject constructor():Person {
    var index = 0
    init {
        index ++
        Log.d("-->","Student init $index")
    }
}
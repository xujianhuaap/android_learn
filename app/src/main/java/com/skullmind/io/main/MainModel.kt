package com.skullmind.io.main

import android.util.Log
import androidx.databinding.BaseObservable
import javax.inject.Inject


class MainModel() : BaseObservable() {

}

class Student @Inject constructor() {
    init {
        Log.d("-->","Student init")
    }
}
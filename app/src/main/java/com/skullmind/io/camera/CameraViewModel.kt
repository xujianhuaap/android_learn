package com.skullmind.io.camera

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CameraViewModel:ViewModel(){
    val name:MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}
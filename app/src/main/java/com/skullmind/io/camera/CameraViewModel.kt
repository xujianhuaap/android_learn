package com.skullmind.io.camera

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class CameraViewModel @Inject constructor():ViewModel(){
    val name:MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}
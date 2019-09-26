package com.skullmind.io.main

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.skullmind.io.BR
import com.skullmind.io.camera.CameraViewModel


class MainModel(val title:String): BaseObservable(){
    @get:Bindable
    var titleCnt:Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.titleCnt)
        }

    @get:Bindable
    var openCammara:CameraViewModel?=null
        set(value) {
            field = value
            notifyPropertyChanged(BR.openCammara)
        }
}
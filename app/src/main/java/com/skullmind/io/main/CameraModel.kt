package com.skullmind.io.main

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

class  CameraModel():BaseObservable(){
    @get:Bindable
    var openStatus:Boolean = true
    set(value) {
        field =value
        notifyPropertyChanged(BR.openStatus)
    }
}
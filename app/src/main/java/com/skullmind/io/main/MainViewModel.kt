package com.skullmind.io.main

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.skullmind.io.BR


class MainViewModel(val title:String): BaseObservable(){
    @get:Bindable
    var titleCnt:Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.titleCnt)
        }
}
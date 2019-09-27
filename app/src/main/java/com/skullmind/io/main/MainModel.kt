package com.skullmind.io.main

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.skullmind.io.BR


class MainModel(): BaseObservable(){
    @get:Bindable
    var title:String =""
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }

    @get:Bindable
    var titleCnt:Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.titleCnt)
        }

}
package com.skullmind.io.main

import android.view.View
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.skullmind.io.camera.CameraModel
import javax.inject.Inject
class MainVM @Inject constructor(private val model: MainModel,
                                 private val cameraModel: CameraModel ){
    fun initData(){

        model?.title = "click me"
    }
    fun clickTitle(view: View){
        var cnt = model?.titleCnt?:0
        model?.titleCnt = ++cnt
    }

    fun clCamera(view:View){
        cameraModel?.openStatus = !(cameraModel.openStatus)!!
    }

    fun getStatus(boolean: Boolean):String {
        var str:String = ""
        if(boolean){
            str = "开启"
        }else{
            str = "关闭"
        }
        return str
    }
}
package com.skullmind.io.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.skullmind.io.main.MainVM

/***
 * 对于setBackgroundColor(int)这个方法重新定义属性
 */
@BindingMethods(
    value =[BindingMethod(
        type = FakeTextView::class,
        attribute = "backgroundColor",
        method = "setBackgroundColor"
    )]
)
class FakeTextView :TextView{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}

/**
 * 重新定义 paddingLeft 这个属性所对应的方法
 */
@BindingAdapter("android:paddingLeft")
fun setPaddingLeft(view:FakeTextView,paddingLeft:Int){
    view.setPadding(paddingLeft,0,paddingLeft,0)
}
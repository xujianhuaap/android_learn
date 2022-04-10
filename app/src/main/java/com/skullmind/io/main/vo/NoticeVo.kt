package com.skullmind.io.main.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
 class NoticeVo(val title:String,val link:String):Parcelable,DialogVo {
    override fun getTitleContent(): String  = title
}

fun getNotices() = listOf<NoticeVo>(
    NoticeVo("南航新会员活动","https://"),
    NoticeVo("快乐飞无限","https://"),
    NoticeVo("加油广州","https://")
)

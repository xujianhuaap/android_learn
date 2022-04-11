package com.skullmind.io.main.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
 class NoticeVo(val title:String,val link:String):Parcelable,DialogVo {
    override fun getTitleContent(): String  = title
}

fun getNotices() = listOf<NoticeVo>(
    NoticeVo("南航新会员活动,欢迎登陆官网领取","https://"),
    NoticeVo("快乐飞无限激情四月，最super的你一起来","https://"),
    NoticeVo("加油广州","https://")
)

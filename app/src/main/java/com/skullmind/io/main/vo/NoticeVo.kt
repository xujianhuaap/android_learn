package com.skullmind.io.main.vo

import android.os.Parcelable
import com.skullmind.io.route.Route
import kotlinx.parcelize.Parcelize

@Parcelize
 class NoticeVo(val title:String,val link:String):Parcelable,DialogVo {
    override fun getTitleContent(): String  = title
}

fun getNotices() = listOf(
    NoticeVo("南航新会员活动,欢迎登陆官网领取","https://"),
    NoticeVo("快乐飞无限激情四月，最super的你一起来","https://"),
    NoticeVo("加油广州","https://"),
    NoticeVo("app 闪退记录",Route.ACTIVITY_ERROR)
)

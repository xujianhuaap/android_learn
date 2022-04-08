package com.skullmind.io.main.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AdvertisementVo(val index:Int,val img: String, val title: String, val type: String) : Parcelable

fun getDefaultAdvertisement() = AdvertisementVo(0,
    "https://www.csair.com/cn/cmsad/resource/b7ed0d0223c78216032ccdc0a54a3eac.jpg",
    "踏春活动",
    "h5"
)

fun getAdvertisements() = listOf<AdvertisementVo>(
    AdvertisementVo(0,
        "https://www.csair.com/cn/cmsad/resource/b7ed0d0223c78216032ccdc0a54a3eac.jpg",
        "踏春活动",
        "h5"
    ),
    AdvertisementVo(1,
        "https://www.csair.com/cn/cmsad/resource/b91f6f267a61f17b55bb175e34d96c65.jpg",
        "奇遇旅行",
        "h5"
    ),
    AdvertisementVo(2,
        "https://www.csair.com/cn/cmsad/resource/75ed5abb5e2dd24d32bd3b7510f29fcf.jpg",
        "会员有礼",
        "h5"
    ),
)
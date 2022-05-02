package com.skullmind.io.main.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecommendItem(val img: String, val title: String, val market: String) : Parcelable

package com.skullmind.io.main.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class FlightInfo(
    val depCity: String,
    val arrCity: String,
    val flightNo: String,
    val depDate: Date,
    val arrDate: Date
) : Parcelable

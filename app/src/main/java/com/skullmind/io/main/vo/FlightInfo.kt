package com.skullmind.io.main.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class FlightInfo(
    val depCity: String,
    val arrCity: String,
    val depPort: String,
    val arrPort: String,
    val flightNo: String,
    val depDate: Date,
    val arrDate: Date,
    val ticketNO:String,
    val bookNo:String
) : Parcelable


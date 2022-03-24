package com.skullmind.io.main

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.skullmind.io.main.vo.FlightInfo
import com.skullmind.io.main.vo.getMenuData
import java.util.*
import java.util.stream.Collectors

class MainViewModel : ViewModel() {
    @SuppressLint("NewApi")
    fun getMenuSources() = getMenuData().stream().limit(16).collect(Collectors.toList())

    fun getFlightInfos() = listOf<FlightInfo>(
        createFlightInfo(),
        createFlightInfo(),
        createFlightInfo(),
        createFlightInfo(),
        createFlightInfo(),
        createFlightInfo(),
        createFlightInfo(),
        createFlightInfo(),
        createFlightInfo(),



        createFlightInfo()
    )

    private fun createFlightInfo(): FlightInfo {
        val depDate = Date(122, 2, 24, 12, 50)
        val arrDate = Date(122, 2, 24, 15, 0)
        return FlightInfo(
            "广州", "汕头", "广州白云", "揭阳潮汕", "CZ9834",
            depDate = depDate, arrDate, "78421578900", "NB41G1"
        )
    }
}
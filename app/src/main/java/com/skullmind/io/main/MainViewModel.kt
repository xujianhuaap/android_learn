package com.skullmind.io.main

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.skullmind.io.main.menu_config.getMenuData
import java.util.stream.Collectors

class MainViewModel:ViewModel() {
    @SuppressLint("NewApi")
    fun getMenuSources() = getMenuData().stream().limit(16).collect(Collectors.toList())
}
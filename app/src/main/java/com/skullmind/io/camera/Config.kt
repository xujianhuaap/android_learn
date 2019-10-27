package com.skullmind.io.camera

import android.util.Log
import android.view.Surface

class Config {
    companion object{
        /***
         * 是否交换高度与宽度
         */
        fun areDimensionsSwapped(displayRotation: Int, sensorOrientation: Int): Boolean {
            var swappedDimensions = false
            when (displayRotation) {
                Surface.ROTATION_0, Surface.ROTATION_180 -> {
                    if (sensorOrientation == 90 || sensorOrientation == 270) {
                        swappedDimensions = true
                    }
                }
                Surface.ROTATION_90, Surface.ROTATION_270 -> {
                    if (sensorOrientation == 0 || sensorOrientation == 180) {
                        swappedDimensions = true
                    }
                }
                else -> {
                    Log.e("Camera", "Display rotation is invalid: $displayRotation")
                }
            }
            return swappedDimensions
        }
    }
}
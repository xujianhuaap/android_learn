package com.skullmind.io

import android.app.Application
import android.content.Context
import com.skullmind.io.error.getErrorParentDir
import java.io.File
import java.io.PrintStream
import java.text.SimpleDateFormat
import java.util.*

class LearnApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Thread.setDefaultUncaughtExceptionHandler(AppExceptionMonitor(this))
    }

}

class AppExceptionMonitor(private val application: LearnApplication) : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(t: Thread, e: Throwable) {
        val errorDir = getErrorParentDir(application)
        if(!errorDir.exists()) errorDir.mkdir()
        val fileName = "error_".plus(SimpleDateFormat("yyyyMMddHHmmss").format(Date())).plus(".txt")
        val errorFile = File(errorDir,fileName)
        if(!errorFile.exists())errorFile.createNewFile()
        val fileOutStream = application.openFileOutput(errorFile.name, Context.MODE_PRIVATE)
        e.printStackTrace(PrintStream(fileOutStream))
    }


}
package com.skullmind.io.error

import android.app.Application
import okio.Buffer
import okio.source
import java.io.File

fun getErrorParentDir(application: Application): File {
    return File(application.filesDir, "error").apply {
        if(!exists()) createNewFile()
    }
}

fun getErrorFiles(application: Application):Array<File>{
    val dir = getErrorParentDir(application)
    return dir.listFiles()
}

fun getErrorDetailInfo(application: Application,file: File):String{
    val fileInputStream = application.openFileInput(file.name)
    val buffer = Buffer()
    fileInputStream.source().read(buffer,1024*10)
    return buffer.readUtf8()
}
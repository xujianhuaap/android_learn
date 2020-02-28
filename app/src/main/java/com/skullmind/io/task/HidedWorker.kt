package com.skullmind.io.task

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class HidedWorker(context:Context,parameters: WorkerParameters) :CoroutineWorker(context,parameters){
    override suspend fun doWork(): Result {
        Log.d("====","time: "+System.currentTimeMillis())
        return Result.Success.retry()
    }
}
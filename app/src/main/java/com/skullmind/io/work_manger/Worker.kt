package com.skullmind.io.work_manger

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*

/***
 * 对于周期worker,最小时间间隔是1.5分钟(90秒)
 *
 * Result.Success Result.Failure 只能用于单次Worker
 *
 * 可以限制worker执行的时机,列如充电过程中
 */
class PeriodWorker(context:Context, parameters: WorkerParameters) :CoroutineWorker(context,parameters){
    override suspend fun doWork(): Result {
        val app = this.inputData.getString("app")
        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
        Log.d("====","$app time: $date")
//        if(date.endsWith("1")) return Result.success()
        return Result.retry()
    }
}

class OneTimeWorker(context: Context,parameters: WorkerParameters):CoroutineWorker(context,parameters){
    override suspend fun doWork(): Result {
        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
        Log.d("====","oneTimeWorker: $date")
        return Result.success()
    }
}
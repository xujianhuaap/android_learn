package com.skullmind.io.task

import android.app.Application
import android.content.Context
import androidx.work.*
import com.skullmind.io.uitils.SHARE_PREFERENCES_KEY_APP_NAME
import com.skullmind.io.uitils.SharePreferencesUtil
import java.util.concurrent.TimeUnit

class Task {
    companion object{
        fun startOneTimeTask(context: Context){
            val oneTimeWorker = OneTimeWorkRequestBuilder<OneTimeWorker>().build()
            WorkManager.getInstance(context).beginWith(oneTimeWorker).enqueue()
        }

        fun startPeriodTask(context: Context){
            val constraints = Constraints.Builder().setRequiresCharging(true).build()
            val data = Data.Builder().putString("app", SharePreferencesUtil.getString(SHARE_PREFERENCES_KEY_APP_NAME)).build()
            val worker = PeriodicWorkRequestBuilder<PeriodWorker>(2, TimeUnit.MINUTES)
                .setInputData(data)
                .setBackoffCriteria(BackoffPolicy.LINEAR,5, TimeUnit.SECONDS)
                .setConstraints(constraints).build()
            WorkManager.getInstance(context).enqueue(worker)
        }
    }
}
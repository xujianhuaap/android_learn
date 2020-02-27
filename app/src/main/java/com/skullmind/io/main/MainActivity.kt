package com.skullmind.io.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.skullmind.io.R
import kotlinx.coroutines.*

val  TAG = MainActivity::class.java.simpleName

class MainActivity : AppCompatActivity(){
    val threadLocal = ThreadLocal<String>()
    /***
     * GlobalScope 启动一个顶级的协程,这个协程会运行整个应用的生命周期,并且永久不能取消,该协程并不能保活进程.
     *
     * runBlocking 不能用于协程,主要设计用来阻塞线程,可以阻塞到该方法执行完毕
     * delay()这个方法只能用于协程,挂起协程并不堵塞线程,对于这样的挂起函数,会检查该协程是否取消
     * job.join()这个方法只能在协程中使用,只有job执行完毕,该方法之后的代码才能进行
     *
     * job.cancel()并不能一定成功,尤其是有计算任务的,需要调用isActive判断.挂起的函数(调用了delay())
     * 都是可以取消的,这样的取消没问题
     *
     * finally 方法体内不能调用挂起函数(delay()),否则爆出异常(CancellationException).如果调用必须withContext(NoneCancelable)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG,"Main Thread")
        runBlocking {
            threadLocal.set("main")
            val job =   launch(Dispatchers.Default+threadLocal.asContextElement()) {
                try {
                    Log.d(TAG,"start delay")
//                    var i = 0
//                    var time = System.currentTimeMillis()
//                    while (isActive){
//                        var nextTime = System.currentTimeMillis()
//                        if( nextTime- time >= 200){
//                            i++
//                            Log.d(TAG,"${i-1}")
//                            time = nextTime
//                        }
//                    }
                    repeat(1000){
                        delay(100)
                        Log.d(TAG,"${it}")
                    }
                    Log.d(TAG,"delay end")
                }finally {
                    withContext(NonCancellable){
                        delay(100)
                        Log.d(TAG,"finally delay")
                    }
                }

            }
            Log.d(TAG,"RunBlocking delay start")
            delay(1000*3)
            Log.d(TAG,"RunBlocking delay End")
            job.cancelAndJoin()
            Log.d(TAG,"job cancel")

            Log.d(TAG,"====")
        }

    }
}

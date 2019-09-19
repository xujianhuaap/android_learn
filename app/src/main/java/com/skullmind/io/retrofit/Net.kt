package com.skullmind.io.retrofit

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object Net{
    var retrofit:Retrofit = initRetrofit()

    fun <T>createService(clazz:Class<T>):T{
         val service = retrofit.create(clazz)
         return service
    }
}

private fun initRetrofit():Retrofit{
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLogin())
        .build()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .callFactory(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build()
    Log.d(Net::class.simpleName,"init retrofit")
    return retrofit
}

class HttpLogin:Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request();
        val resp = chain.proceed(req)
        Log.d(Net::class.java.name,"=================================")
        Log.d(Net::class.java.name,req.url().toString())
        Log.d(Net::class.java.name,resp.networkResponse().toString())
        Log.d(Net::class.java.name,"=================================")
        return resp
    }
}
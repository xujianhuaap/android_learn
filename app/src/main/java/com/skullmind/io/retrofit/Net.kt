package com.skullmind.io.retrofit

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Net{
    var retrofit:Retrofit = initRetrofit()

    fun <T>createService(clazz:Class<T>):T{
         val service = retrofit.create(clazz)
         return service
    }
}

private fun initRetrofit():Retrofit{
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    Log.d(Net::class.simpleName,"init retrofit")
    return retrofit
}

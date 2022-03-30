package com.skullmind.io

import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object Net {
    private fun init() =  Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    fun <T>createService(clazz: Class<T>) = init().create(clazz)
}

interface User {
    @GET("users/{username}")
    fun getInfo(@Path("username") username:String):Observable<JsonObject>

    @GET("users/{username}/repos")
    fun getRepos(@Path("username") username:String):Observable<JsonObject>
}
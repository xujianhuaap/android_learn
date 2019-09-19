package com.skullmind.io.data

import com.skullmind.io.github.bean.GitHub
import com.skullmind.io.github.bean.User

import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable


interface GitHubService {
    @GET("/users/@{user}")
    fun reqGitHub(@Path(value = "user") name: String): Observable<GitHub>

    @GET("/users/xujianhuaap/followers")
    fun reqFollowers(): Observable<List<User>>

}
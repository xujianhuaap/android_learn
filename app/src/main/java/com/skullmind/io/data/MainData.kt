package com.skullmind.io.data

import com.skullmind.io.main.GitHub
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface GitHubService {
    @GET("/")
    fun listGitHub(): Call<GitHub>
}
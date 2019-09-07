package com.skullmind.io.data

import com.skullmind.io.data.bean.GitHub
import retrofit2.http.GET
import rx.Observable


interface GitHubService {
    @GET("/")
    fun listGitHub(): Observable<GitHub>
}
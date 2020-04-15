package com.edmundo.domain.datasource

import com.edmundo.domain.model.CommitResponse
import com.edmundo.domain.model.Repository
import com.edmundo.domain.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface QrCodeScannerService {

    @GET("/users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): Response<User>

    @GET("/users/{username}/repos")
    suspend fun getRepositories(
        @Path("username") username: String
    ): Response<List<Repository>>

    @GET("/repos/{owner}/{repository}/commits")
    suspend fun getCommits(
        @Path("owner") owner: String,
        @Path("repository") repository: String
    ): Response<List<CommitResponse>>

}
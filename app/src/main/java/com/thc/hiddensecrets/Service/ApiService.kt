package com.thc.hiddensecrets.Service

import com.thc.hiddensecrets.network.request.CreateUserRequest
import com.thc.hiddensecrets.network.request.LoginRequest
import com.thc.hiddensecrets.network.response.CreateUserResponse
import com.thc.hiddensecrets.network.response.DadosResponse
import com.thc.hiddensecrets.network.response.LoginResponse
import com.thc.hiddensecrets.network.response.NewsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/createUser")
    suspend fun createUser(@Body createUser: CreateUserRequest): Response<CreateUserResponse>

    @GET("/ibovespa")
    suspend fun ibovespa(): Response<DadosResponse>

    @GET("/news")
    suspend fun news(@Query("q") q: String, @Query("from") from: String, @Query("language") language: String): Response<NewsResponse>

}
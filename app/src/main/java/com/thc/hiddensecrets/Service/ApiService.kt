package com.thc.hiddensecrets.Service

import com.thc.hiddensecrets.network.request.CreateUserRequest
import com.thc.hiddensecrets.network.request.LoginRequest
import com.thc.hiddensecrets.network.response.CreateUserResponse
import com.thc.hiddensecrets.network.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/createUser")
    suspend fun createUser(@Body createUser : CreateUserRequest): Response<CreateUserResponse>

}
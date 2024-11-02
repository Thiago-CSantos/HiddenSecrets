package com.thc.hiddensecrets.network

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(private val context: Context) {

    private val BASE_URL = "http://10.0.2.2:3000/"

    private val retrofit: Retrofit by lazy {
        val sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwtToken", "") ?: ""

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(token))
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}

package com.abdoul.dex.retrofit

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHelper {

    var BASE_URL = "BASE_URL"
    var GOOGLECLIENT = "https://www.googleapis.com/oauth2/v4/"
    var BREEZOMETER = "https://api.breezometer.com/air-quality/v2/"



    fun create(token: String): ApiInterface {

        var client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        }.build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(ApiInterface::class.java)
    }

    fun getAuthToken(): ApiInterface {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(GOOGLECLIENT)
            .build()
        return retrofit.create(ApiInterface::class.java)
    }

    fun getBreezoMeter(): ApiInterface {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BREEZOMETER)
            .build()
        return retrofit.create(ApiInterface::class.java)
    }
}
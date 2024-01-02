package com.abdoul.dex.retrofit

import com.abdoul.dex.models.GoogleSignInAccessTokenDataClass
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("fcm_token") fcm_token: String,
    ): Call<JsonObject>


    @FormUrlEncoded
    @POST("register")
    fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String,
        @Field("fcm_token") fcm_token: String,
        @Field("phone") phone: String,
    ): Call<JsonObject>

    @FormUrlEncoded
    @POST("forgot-password")
    fun forgetPassword(
        @Field("email") email: String
    ): Call<JsonObject>


    @FormUrlEncoded
    @POST("reset-password")
    fun resetPassword(
        @Field("email") email: String,
        @Field("code") code: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String,
    ): Call<JsonObject>

    @GET("profile")
    fun getProfile(): Call<JsonObject>

    @FormUrlEncoded
    @POST
    fun getAccessTokenGoogle(
        @Url url: String,
        @Field("grant_type") grant_type: String,
        @Field("client_id") client_id: String,
        @Field("client_secret") client_secret: String,
        @Field("redirect_uri") redirect_uri: String,
        @Field("code") authCode: String,
        @Field("id_token") id_token: String
    ): Call<GoogleSignInAccessTokenDataClass>

    @FormUrlEncoded
    @POST("auth")
    fun socialLogin(
        @Field("provider") provider: String,
        @Field("token") token: String,
        @Field("fcm_token") fcm_token: String,
    ): Call<JsonObject>

    @GET("current-conditions?")
    fun getMAp(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("key") key: String,
    ): Call<JsonObject>

}

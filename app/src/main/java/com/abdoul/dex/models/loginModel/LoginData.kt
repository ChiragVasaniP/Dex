package com.abdoul.dex.models.loginModel

import com.google.gson.annotations.SerializedName


data class LoginData(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("email_verified_at") var emailVerifiedAt: String? = null,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("birth_date") var birthDate: String? = null,
    @SerializedName("api_token") var apiToken: String? = null,
    @SerializedName("picture_path") var picturePath: String? = null,
    @SerializedName("facebook") var facebook: String? = null,
    @SerializedName("twitter") var twitter: String? = null,
    @SerializedName("instagram") var instagram: String? = null,
    @SerializedName("provider") var provider: String? = null,
    @SerializedName("provider_id") var providerId: String? = null,
    @SerializedName("is_admin") var isAdmin: Int? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("deleted_at") var deletedAt: String? = null,
    @SerializedName("avatar") var avatar: String? = null,
    @SerializedName("age") var age: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("addresses") var addresses: ArrayList<String> = arrayListOf()

)
package com.abdoul.dex.models

import com.google.gson.annotations.SerializedName

data class GoogleSignInAccessTokenDataClass(
    @SerializedName("access_token") val access_token: String,
    @SerializedName("expires_in") val expires_in: Int,
    @SerializedName("id_token") val id_token: String,
    @SerializedName("token_type") val token_type: String
)

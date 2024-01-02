package com.abdoul.dex.models.loginModel

import com.google.gson.annotations.SerializedName


data class LoginMainDataModel(

    @SerializedName("success") var success: Boolean? = null,
    @SerializedName("data") var loginData: LoginData? = LoginData(),
    @SerializedName("message") var message: String? = null


)
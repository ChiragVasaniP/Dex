package com.abdoul.dex.models.registerModel

import com.abdoul.dex.models.loginModel.LoginData
import com.google.gson.annotations.SerializedName


data class RegisterMainModel(

    @SerializedName("success") var success: Boolean? = null,
    @SerializedName("data") var data: LoginData? = LoginData(),
    @SerializedName("message") var message: String? = null

)
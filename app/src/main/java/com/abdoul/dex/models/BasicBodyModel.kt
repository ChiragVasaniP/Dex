package com.abdoul.dex.models

import com.google.gson.annotations.SerializedName


data class BasicBodyModel(

    @SerializedName("success") var success: Boolean? = null,
    @SerializedName("data") var data: ArrayList<String> = arrayListOf(),
    @SerializedName("message") var message: String? = null

)


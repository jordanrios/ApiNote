package com.example.apinote.app.data.api.response

import com.google.gson.annotations.SerializedName

data class InfoResponse(
    @SerializedName("count") val count:Int,
    @SerializedName("pages") val pages:Int,
    @SerializedName("next") val next:String?,
    @SerializedName("prev") val prev:String?,
)
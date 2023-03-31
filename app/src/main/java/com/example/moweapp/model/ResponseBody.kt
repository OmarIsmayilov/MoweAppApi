package com.example.moweapp.model

import com.google.gson.annotations.SerializedName

data class ResponseBody(
    @SerializedName("value") val value: Double
)
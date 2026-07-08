package com.mito.network.auth.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: String,
)

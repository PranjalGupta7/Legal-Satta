package com.example.legalsatta.Models

import com.google.gson.annotations.SerializedName

data class UsernameVerificationModel(
    @SerializedName("status")
    var status: String,
    @SerializedName("isAvailable")
    var isAvailable: Boolean
    )

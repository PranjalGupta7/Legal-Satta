package com.example.legalsatta.Models

import com.google.gson.annotations.SerializedName

data class UpcomingMatchesResponseModel(
    @SerializedName("status")
    var status: String,

    @SerializedName("result")
    var result: ArrayList<Results>
)


package com.example.legalsatta.Models

import com.google.gson.annotations.SerializedName

data class PredictedTeamResponseModel(
    @SerializedName("status")
    var status: String,
    @SerializedName("predictedteam")
    var predictedTeam: String
)

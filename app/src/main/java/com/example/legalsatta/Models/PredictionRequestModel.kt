package com.example.legalsatta.Models

import com.google.gson.annotations.SerializedName

data class PredictionRequestModel(
    @SerializedName("userid")
    var userId: String,
    @SerializedName("predictedteam")
    var predictedTeam: String,
    @SerializedName("matchid")
    var matchId: String
)

package com.example.legalsatta.Models

data class Prediction(
    val date: Int,
    val match: Match,
    val predictedteam: String,
    val result: Boolean
)
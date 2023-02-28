package com.example.legalsatta.Models

import com.google.gson.annotations.SerializedName

data class RegistrationModel(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("name")
    val name: String
    )

data class LoginModel(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
)

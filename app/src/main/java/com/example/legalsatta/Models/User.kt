package com.example.legalsatta.Models

data class User(
    val email: String,
    val score: Int,
    val token: String,
    val username: String,
    val id: String
)

lateinit var loginedUser: User
package com.example.legalsatta.Models

import com.google.gson.annotations.SerializedName

data class LatestMatchs(

	@field:SerializedName("result")
	val result: Results? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Team(

	@field:SerializedName("coverimg")
	val coverimg: String? = null,

	@field:SerializedName("rate")
	val rate: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class Results(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("winner")
	val winner: Any? = null,

	@field:SerializedName("team1")
	val team1: Team? = null,

	@field:SerializedName("team2")
	val team2: Team? = null,

	@field:SerializedName("id")
	val id: String? = null
)

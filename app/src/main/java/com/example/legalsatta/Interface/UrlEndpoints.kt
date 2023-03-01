package com.example.legalsatta.Interface

import android.database.Observable
import com.example.legalsatta.Models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UrlEndpoints {

//    @GET("/user/user-exist/{username}")
//    suspend fun getUsernameAuthentication(@Path("username") username: String): Response<UsernameVerificationModel>

    @POST("/auth/signup")
    suspend fun postRegistrationDetails(@Body userRegistrationModel: RegistrationModel): Response<TokenModel>

    @GET("/match/latest")
    suspend fun getLatestMatch() : Response<LatestMatchs>

    @GET("/match/upcoming")
    suspend fun getUpcomingMatches(): Response<UpcomingMatchesResponseModel>

    @POST("/auth/login")
    suspend fun postLoginDetails(@Body userLoginModel: LoginModel): Response<TokenModel>

    @GET("/leaderboard/get")
    suspend fun getLeaderBoardList(): Response<LeaderBoardUserListModel>

    @POST("/user/predict")
    suspend fun setUserPrediction(@Body prediction: PredictionRequestModel): Response<PredictedTeamResponseModel>

}
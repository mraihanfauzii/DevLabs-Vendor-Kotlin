package com.hackathon.devlabsvendor.api

import com.hackathon.devlabsvendor.model.AddMessageRequest
import com.hackathon.devlabsvendor.model.AddMessageResponse
import com.hackathon.devlabsvendor.model.AddPortfolioRequest
import com.hackathon.devlabsvendor.model.AddPortfolioResponse
import com.hackathon.devlabsvendor.model.DeleteResponse
import com.hackathon.devlabsvendor.model.GetMessageResponse
import com.hackathon.devlabsvendor.model.GetPortfolioResponse
import com.hackathon.devlabsvendor.model.LoginRequest
import com.hackathon.devlabsvendor.model.LoginResponse
import com.hackathon.devlabsvendor.model.RegisterRequest
import com.hackathon.devlabsvendor.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("users/register")
    fun register (
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>

    @POST("users/login")
    fun login (
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @POST("portofolios")
    fun getPortfolio (
        @Header("Authorization") token: String,
        @Query("architect_id") architectId: String,
    ): Call<GetPortfolioResponse>

    @POST("portofolios")
    fun addPortfolio (
        @Header("Authorization") token: String,
        @Body addPortfolioRequest: AddPortfolioRequest
    ): Call<AddPortfolioResponse>

    @POST("portofolios")
    fun deletePortfolio (
        @Header("Authorization") token: String,
        @Query("architect_id") architectId: String,
    ): Call<DeleteResponse>

    @POST("users/login")
    fun getMessage (
        @Header("Authorization") token: String,
        @Query("first_user_id") firstUserId: String,
        @Query("second_user_id") secondUserId: String,
    ): Call<GetMessageResponse>

    @POST("users/login")
    fun addMessage (
        @Header("Authorization") token: String,
        @Body addMessageRequest: AddMessageRequest
    ): Call<AddMessageResponse>
}
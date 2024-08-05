package com.hackathon.devlabsvendor.api

import com.hackathon.devlabsvendor.model.AddMessageData
import com.hackathon.devlabsvendor.model.AddMessageRequest
import com.hackathon.devlabsvendor.model.AddPortfolioData
import com.hackathon.devlabsvendor.model.AddPortfolioRequest
import com.hackathon.devlabsvendor.model.ApiResponse
import com.hackathon.devlabsvendor.model.DeleteResponse
import com.hackathon.devlabsvendor.model.LastMessage
import com.hackathon.devlabsvendor.model.LoginData
import com.hackathon.devlabsvendor.model.LoginRequest
import com.hackathon.devlabsvendor.model.Message
import com.hackathon.devlabsvendor.model.Portfolio
import com.hackathon.devlabsvendor.model.RegisterData
import com.hackathon.devlabsvendor.model.RegisterRequest
import com.hackathon.devlabsvendor.model.UpdateProfileData
import com.hackathon.devlabsvendor.model.UserData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @POST("users/register")
    fun register (
        @Body registerRequest: RegisterRequest
    ): Call<ApiResponse<RegisterData>>

    @POST("users/login")
    fun login (
        @Body loginRequest: LoginRequest
    ): Call<ApiResponse<LoginData>>

    @DELETE("users/logout")
    fun logout (
        @Header("Authorization") token: String
    ): Call<DeleteResponse>

    @GET("users")
    fun getProfile (
        @Header("Authorization") token: String,
    ): Call<ApiResponse<UserData>>

    @Multipart
    @PUT("users")
    fun putProfile (
        @Header("Authorization") token: String,
        @Part("profile_name") profileName: RequestBody,
        @Part("profile_description") profileDescription: RequestBody,
        @Part("phonenumber") phoneNumber: RequestBody,
        @Part profile_picture: MultipartBody.Part
    ): Call<ApiResponse<UpdateProfileData>>

    @GET("messages/last")
    fun getLastMessage (
        @Header("Authorization") token: String,
    ): Call<ApiResponse<List<LastMessage>>>

    @GET("messages")
    fun getMessage (
        @Header("Authorization") token: String,
        @Query("first_user_id") firstUserId: String,
        @Query("second_user_id") secondUserId: String,
    ): Call<ApiResponse<List<Message>>>

    @POST("messages")
    fun addMessage (
        @Header("Authorization") token: String,
        @Body addMessageRequest: AddMessageRequest
    ): Call<ApiResponse<AddMessageData>>

    @POST("portofolios")
    fun addPortfolio (
        @Header("Authorization") token: String,
        @Body addPortfolioRequest: AddPortfolioRequest
    ): Call<ApiResponse<AddPortfolioData>>

    @GET("portofolios")
    fun getPortfolio (
        @Header("Authorization") token: String,
        @Query("architect_id") architectId: String,
    ): Call<ApiResponse<List<Portfolio>>>

    @Multipart
    @PUT("portofolios")
    fun putPortfolio (
        @Header("Authorization") token: String,
        @Part("name") profileDescription: RequestBody,
        @Part("description") phoneNumber: RequestBody,
        @Part attachment_files: MultipartBody.Part
    )

    @DELETE("portofolios")
    fun deletePortfolio (
        @Header("Authorization") token: String,
        @Query("architect_id") architectId: String,
    ): Call<DeleteResponse>
}
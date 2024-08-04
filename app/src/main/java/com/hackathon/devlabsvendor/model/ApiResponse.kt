package com.hackathon.devlabsvendor.model

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val code: Int,
    val data: T
)
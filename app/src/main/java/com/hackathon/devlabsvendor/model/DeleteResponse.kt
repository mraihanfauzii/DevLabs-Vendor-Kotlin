package com.hackathon.devlabsvendor.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class DeleteResponse(
    val success: Boolean,
    val message: String,
    val code: Int
)
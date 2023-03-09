package com.rhm.capstone.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class GameResponse(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("released")
    val releaseDate: String?,
    @field:SerializedName("background_image")
    val imageUrl: String?,
    @field:SerializedName("description_raw")
    val description: String?
)

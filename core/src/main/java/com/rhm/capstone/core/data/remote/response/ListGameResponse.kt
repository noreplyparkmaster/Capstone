package com.rhm.capstone.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ListGameResponse(
    @field:SerializedName("results")
    val games: List<GameResponse>
)

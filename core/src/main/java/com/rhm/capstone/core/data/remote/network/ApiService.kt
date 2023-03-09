package com.rhm.capstone.core.data.remote.network

import com.rhm.capstone.core.BuildConfig
import com.rhm.capstone.core.data.remote.response.GameResponse
import com.rhm.capstone.core.data.remote.response.ListGameResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    suspend fun getAllGames(
        @Query("key") apiKey: String = BuildConfig.RAWG_API_KEY,
        @Query("search") query: String
    ): ListGameResponse

    @GET("games/{id}")
    suspend fun getGameDetail(
        @Path("id") id: Int,
        @Query("key") apiKey: String = BuildConfig.RAWG_API_KEY,
    ): GameResponse
}
package com.rhm.capstone.core.data.remote

import com.rhm.capstone.core.data.remote.network.ApiResponse
import com.rhm.capstone.core.data.remote.network.ApiService
import com.rhm.capstone.core.data.remote.response.GameResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllGames(query: String): Flow<ApiResponse<List<GameResponse>>> = flow {
        try {
            val response = apiService.getAllGames(query = query)
            val dataArray = response.games
            if (dataArray.isNotEmpty()) {
                emit(ApiResponse.Success(dataArray))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getGameDetail(id: Int): Flow<ApiResponse<GameResponse>> = flow {
        try {
            val game = apiService.getGameDetail(id)
            emit(ApiResponse.Success(game))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)
}
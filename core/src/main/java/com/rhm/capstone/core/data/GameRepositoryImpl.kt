package com.rhm.capstone.core.data

import android.util.Log
import com.rhm.capstone.core.data.local.LocalDataSource
import com.rhm.capstone.core.data.remote.RemoteDataSource
import com.rhm.capstone.core.data.remote.network.ApiResponse
import com.rhm.capstone.core.domain.model.Game
import com.rhm.capstone.core.domain.repository.GameRepository
import com.rhm.capstone.core.utils.AppExecutors
import com.rhm.capstone.core.utils.toDomain
import com.rhm.capstone.core.utils.toEntity
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
) : GameRepository {

    override fun getAllGames(query: String): Flow<Resource<List<Game>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.getAllGames(query).first()) {
            is ApiResponse.Success -> {
                val games = apiResponse.data.map { it.toDomain() }
                emit(Resource.Success(games))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Success(emptyList()))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getGameDetail(id: Int): Flow<Resource<Game>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.getGameDetail(id).first()) {
            is ApiResponse.Success -> {
                var gameEntity = apiResponse.data.toEntity()
                val isFavorite = localDataSource.isFavorite(gameEntity.id)
                gameEntity = gameEntity.copy(isFavorite = isFavorite)
                appExecutors.diskIO().execute { localDataSource.insertGame(gameEntity) }
                emit(Resource.Success(gameEntity.toDomain()))
            }
            is ApiResponse.Error -> {
                val game = localDataSource.getDetailGame(id)
                    .map { it?.toDomain() }
                    .map { Resource.Error(apiResponse.errorMessage, it) }
                emitAll(game)
            }
            else -> {}
        }
    }

    override fun getFavoriteGames(): Flow<List<Game>> {
        return localDataSource.getFavoriteGames().map { gameEntities ->
            gameEntities.map { it.toDomain() }
        }
    }

    override fun setFavoriteGame(game: Game, state: Boolean) {
        val gameEntity = game.toEntity()
        appExecutors.diskIO().execute { localDataSource.setFavoriteGame(gameEntity, state) }
    }

}
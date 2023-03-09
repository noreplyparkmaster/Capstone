package com.rhm.capstone.core.data.local

import com.rhm.capstone.core.data.local.entity.GameEntity
import com.rhm.capstone.core.data.local.room.GameDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val gameDao: GameDao) {

    fun getAllGames(): Flow<List<GameEntity>> = gameDao.getAllGames()

    fun getDetailGame(id: Int): Flow<GameEntity?> = gameDao.getDetailGame(id)

    fun getFavoriteGames(): Flow<List<GameEntity>> = gameDao.getFavoriteGames()

    suspend fun isFavorite(id: Int) = gameDao.isNewFavorite(id)

    fun insertGame(game: GameEntity) = gameDao.insertGame(game)

    fun setFavoriteGame(game: GameEntity, newState: Boolean) {
        game.isFavorite = newState
        gameDao.updateGame(game)
    }
}
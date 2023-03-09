package com.rhm.capstone.core.data.local.room

import androidx.room.*
import com.rhm.capstone.core.data.local.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM game")
    fun getAllGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM game WHERE id = :id")
    fun getDetailGame(id: Int): Flow<GameEntity?>

    @Query("SELECT * FROM game where isFavorite = 1")
    fun getFavoriteGames(): Flow<List<GameEntity>>

    @Query("SELECT EXISTS(SELECT * FROM game WHERE id = :id AND isFavorite = 1)")
    suspend fun isNewFavorite(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game: GameEntity)

    @Update
    fun updateGame(game: GameEntity)



}
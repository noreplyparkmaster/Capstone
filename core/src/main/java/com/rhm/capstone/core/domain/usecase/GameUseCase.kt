package com.rhm.capstone.core.domain.usecase

import com.rhm.capstone.core.data.Resource
import com.rhm.capstone.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GameUseCase {
    fun getAllGames(query: String): Flow<Resource<List<Game>>>
    fun getGameDetail(id: Int): Flow<Resource<Game>>
    fun getFavoriteGames(): Flow<List<Game>>
    fun setFavoriteGame(game: Game, state: Boolean)
}
package com.rhm.capstone.core.domain.usecase

import com.rhm.capstone.core.domain.model.Game
import com.rhm.capstone.core.domain.repository.GameRepository
import javax.inject.Inject

class GameInteractor @Inject constructor(private val gameRepository: GameRepository): GameUseCase {
    override fun getAllGames(query: String) = gameRepository.getAllGames(query)
    override fun getGameDetail(id: Int) = gameRepository.getGameDetail(id)
    override fun getFavoriteGames() = gameRepository.getFavoriteGames()
    override fun setFavoriteGame(game: Game, state: Boolean) = gameRepository.setFavoriteGame(game, state)
}
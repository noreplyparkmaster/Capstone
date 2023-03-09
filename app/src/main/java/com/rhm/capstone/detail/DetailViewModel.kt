package com.rhm.capstone.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rhm.capstone.core.domain.model.Game
import com.rhm.capstone.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val gameUseCase: GameUseCase) : ViewModel() {
    fun getDetailGame(id: Int) = gameUseCase.getGameDetail(id).asLiveData()
    fun setFavoriteGame(game: Game, newStatus: Boolean) = gameUseCase.setFavoriteGame(game, newStatus)
}
package com.rhm.capstone.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rhm.capstone.core.domain.usecase.GameUseCase

class FavoriteViewModel(gameUseCase: GameUseCase) : ViewModel() {
    val favoriteGames = gameUseCase.getFavoriteGames().asLiveData()
}
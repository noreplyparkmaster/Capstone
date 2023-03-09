package com.rhm.capstone.home

import androidx.lifecycle.*
import com.rhm.capstone.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(gameUseCase: GameUseCase) : ViewModel() {

    private val searchQuery = MutableStateFlow("")
    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }

    val games = searchQuery
        .debounce(500)
        .distinctUntilChanged()
        .flatMapLatest {
            gameUseCase.getAllGames(it)
        }
        .asLiveData()
}
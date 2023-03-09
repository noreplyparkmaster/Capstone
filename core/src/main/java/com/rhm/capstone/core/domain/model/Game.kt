package com.rhm.capstone.core.domain.model

data class Game(
    val id: Int,
    val name: String,
    val releaseDate: String,
    val imageUrl: String,
    val description: String,
    var isFavorite: Boolean,
)
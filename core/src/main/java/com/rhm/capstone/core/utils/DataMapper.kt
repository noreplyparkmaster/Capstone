package com.rhm.capstone.core.utils

import com.rhm.capstone.core.data.local.entity.GameEntity
import com.rhm.capstone.core.data.remote.response.GameResponse
import com.rhm.capstone.core.domain.model.Game

fun GameResponse.toDomain() = Game(
    id = id,
    name = name,
    releaseDate = releaseDate ?: "",
    imageUrl = imageUrl ?: "",
    description = description ?: "",
    isFavorite = false
)

fun GameResponse.toEntity() = GameEntity(
    id = id,
    name = name,
    releaseDate = releaseDate ?: "",
    imageUrl = imageUrl ?: "",
    description = description ?: "",
    isFavorite = false
)

fun GameEntity.toDomain() = Game(
    id = id,
    name = name,
    releaseDate = releaseDate,
    imageUrl = imageUrl,
    description = description,
    isFavorite = isFavorite
)

fun Game.toEntity() = GameEntity(
    id = id,
    name = name,
    releaseDate = releaseDate,
    imageUrl = imageUrl,
    description = description,
    isFavorite = isFavorite
)
package com.rhm.capstone.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class GameEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val releaseDate: String,
    val imageUrl: String,
    val description: String,
    var isFavorite: Boolean = false
)

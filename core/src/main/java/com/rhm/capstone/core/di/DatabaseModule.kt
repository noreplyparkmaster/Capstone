package com.rhm.capstone.core.di

import android.content.Context
import androidx.room.Room
import com.rhm.capstone.core.data.local.room.GameDao
import com.rhm.capstone.core.data.local.room.GameDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GameDatabase = Room.databaseBuilder(
        context,
        GameDatabase::class.java, "Game.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideGameDao(database: GameDatabase): GameDao = database.gameDao()
}
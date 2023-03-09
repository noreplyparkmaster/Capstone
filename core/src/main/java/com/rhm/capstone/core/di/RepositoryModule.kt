package com.rhm.capstone.core.di

import com.rhm.capstone.core.data.GameRepositoryImpl
import com.rhm.capstone.core.domain.repository.GameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(gameRepositoryImpl: GameRepositoryImpl): GameRepository

}
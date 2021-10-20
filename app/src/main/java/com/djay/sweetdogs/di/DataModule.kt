package com.djay.sweetdogs.di

import com.djay.sweetdogs.data.repository.DogsRepositoryImpl
import com.djay.sweetdogs.domain.repository.DogsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDogsRepository(dogsRepository: DogsRepositoryImpl): DogsRepository = dogsRepository

}

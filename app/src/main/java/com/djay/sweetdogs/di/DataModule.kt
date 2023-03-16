package com.djay.sweetdogs.di

import com.djay.sweetdogs.data.repository.DogsRepositoryImpl
import com.djay.sweetdogs.domain.repository.DogsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DataModule {

    @Binds
    fun bindDogsRepository(impl: DogsRepositoryImpl): DogsRepository
}

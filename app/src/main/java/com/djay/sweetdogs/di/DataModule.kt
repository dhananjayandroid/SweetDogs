package com.djay.sweetdogs.di

import com.djay.sweetdogs.data.repository.DogsRepositoryImpl
import com.djay.sweetdogs.domain.repository.DogsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
interface DataModule {

    @Binds
    fun bindDogsRepository(impl: DogsRepositoryImpl): DogsRepository
}

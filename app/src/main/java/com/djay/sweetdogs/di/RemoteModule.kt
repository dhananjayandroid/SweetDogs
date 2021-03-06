package com.djay.sweetdogs.di

import com.djay.sweetdogs.BuildConfig
import com.djay.sweetdogs.data.remote.api.DogsService
import com.djay.sweetdogs.data.remote.api.ServiceFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideDogService(): DogsService {
        return ServiceFactory.create(BuildConfig.DEBUG, BuildConfig.BASE_URL)
    }
}

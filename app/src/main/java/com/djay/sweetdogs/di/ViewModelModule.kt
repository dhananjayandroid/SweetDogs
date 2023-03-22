package com.djay.sweetdogs.di

import com.djay.sweetdogs.data.remote.api.DogsService
import com.djay.sweetdogs.data.repository.ResultHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideDogService(retrofit: Retrofit): DogsService {
        return retrofit.create(DogsService::class.java)
    }

    @Provides
    fun providesCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    @Provides
    fun providesResultHandler(): ResultHandler {
        return ResultHandler()
    }
}

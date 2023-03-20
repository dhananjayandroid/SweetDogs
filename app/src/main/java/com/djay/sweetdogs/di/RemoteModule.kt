package com.djay.sweetdogs.di

import com.djay.sweetdogs.data.remote.api.DogsService
import com.djay.sweetdogs.data.remote.mapper.DogsResponseMapper
import com.djay.sweetdogs.data.remote.mapper.ResponseMapper
import com.djay.sweetdogs.data.remote.model.DogDTO
import com.djay.sweetdogs.data.repository.DogsRepositoryDelegate
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
object RemoteModule {

    @Provides
    fun provideDogService(retrofit: Retrofit): DogsService {
        return retrofit.create(DogsService::class.java)
    }

    @Provides
    fun providesCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    @Provides
    fun providesDogsRepositoryDelegate(
        dogsService: DogsService,
        mapper: ResponseMapper<List<DogDTO>>,
        coroutineScope: CoroutineScope
    ): DogsRepositoryDelegate {
        return DogsRepositoryDelegate(dogsService, mapper, coroutineScope)
    }

    @Provides
    fun providesDogsResponseMapper(): ResponseMapper<List<DogDTO>> {
        return DogsResponseMapper()
    }
}

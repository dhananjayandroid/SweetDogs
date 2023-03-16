package com.djay.sweetdogs.di

import com.djay.sweetdogs.presentation.utils.CoroutineContextProvider
import com.djay.sweetdogs.presentation.utils.CoroutineContextProviderImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object PresentationModule {

    @Provides
    fun provideCoroutineContextProvider(contextProvider: CoroutineContextProviderImp): CoroutineContextProvider =
        contextProvider

}

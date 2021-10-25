package com.djay.sweetdogs.presentation.dogslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.djay.sweetdogs.domain.pagination.DogDataSourceFactory
import com.djay.sweetdogs.domain.model.Dog
import com.djay.sweetdogs.presentation.BaseViewModel
import com.djay.sweetdogs.utils.CoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class DogsListViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val dogDataSourceFactory: DogDataSourceFactory
) : BaseViewModel(contextProvider) {

    private val _dogsList = MutableLiveData<PagingData<Dog>>()
    private var dogsList: LiveData<PagingData<Dog>> = _dogsList

    fun getDogs(): LiveData<PagingData<Dog>> {
        return dogsList
    }

    fun retrieveDogs() {
        launchCoroutineIO {
            dogDataSourceFactory.getDogList().cachedIn(this).collect {
                _dogsList.postValue(it)
            }
        }
    }

}

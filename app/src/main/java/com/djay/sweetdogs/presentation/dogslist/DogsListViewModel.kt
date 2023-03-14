package com.djay.sweetdogs.presentation.dogslist

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.djay.sweetdogs.domain.model.Dog
import com.djay.sweetdogs.domain.paging.DogPagingSource
import com.djay.sweetdogs.domain.usecases.GetDogsListUseCase
import com.djay.sweetdogs.presentation.BaseViewModel
import com.djay.sweetdogs.presentation.utils.CoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DogsListViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val getDogsListUseCase: GetDogsListUseCase
) : BaseViewModel(contextProvider) {

    private val _dogsList = MutableStateFlow<PagingData<Dog>>(PagingData.empty())
    var dogsList: StateFlow<PagingData<Dog>> = _dogsList

    private val _selectedDog = MutableSharedFlow<Dog>()
    val selectedDog: SharedFlow<Dog> = _selectedDog

    init {
        launchCoroutineIO {
            getDogsListUseCase.invoke(
                GetDogsListUseCase.Params(
                    DogPagingSource.Constants.PAGE_SIZE,
                    DogPagingSource.Constants.BREED
                )
            ).cachedIn(this).collect {
                _dogsList.value = it
            }
        }
    }

    fun onDogSelected(dog: Dog) {
        launchCoroutineMain {
            _selectedDog.emit(dog)
        }
    }
}

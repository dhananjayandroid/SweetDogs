package com.djay.sweetdogs.presentation.dogslist

import androidx.paging.PagingData
import com.djay.sweetdogs.domain.model.Dog
import com.djay.sweetdogs.domain.paging.DogPagingSource
import com.djay.sweetdogs.domain.usecases.GetDogsListUseCase
import com.djay.sweetdogs.presentation.BaseViewModel
import com.djay.sweetdogs.presentation.mapper.mapResultToDogsUIStateFlow
import com.djay.sweetdogs.presentation.state.UIState
import com.djay.sweetdogs.utils.CoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DogsListViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val getDogsListUseCase: GetDogsListUseCase
) : BaseViewModel(contextProvider) {

    private val _dogsList: MutableStateFlow<UIState<PagingData<Dog>>> =
        MutableStateFlow(UIState.Loading(true))
    var dogsList: StateFlow<UIState<PagingData<Dog>>> = _dogsList.asStateFlow()
    private val _selectedDog = MutableSharedFlow<Dog>()
    val selectedDog: SharedFlow<Dog> = _selectedDog

    init {
        retrieveDogsList()
    }

    private fun retrieveDogsList() {
        launchCoroutineIO {
            val responseResult =
                getDogsListUseCase(
                    GetDogsListUseCase.Params(
                        DogPagingSource.PAGE_SIZE,
                        DogPagingSource.BREED
                    )
                )
            _dogsList.emitAll(responseResult.mapResultToDogsUIStateFlow())
        }
    }

    fun onDogSelected(dog: Dog) {
        launchCoroutineMain {
            _selectedDog.emit(dog)
        }
    }
}

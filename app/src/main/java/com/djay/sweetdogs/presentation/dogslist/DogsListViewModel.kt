package com.djay.sweetdogs.presentation.dogslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.djay.sweetdogs.domain.model.Dog
import com.djay.sweetdogs.domain.paging.PagingConstants.Companion.BREED
import com.djay.sweetdogs.domain.usecases.GetDogsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogsListViewModel @Inject constructor(
    private val getDogsListUseCase: GetDogsListUseCase
) : ViewModel() {

    private val _dogsList = MutableStateFlow<PagingData<Dog>>(PagingData.empty())
    var dogsList: StateFlow<PagingData<Dog>> = _dogsList.asStateFlow()
    private val _selectedDog = MutableSharedFlow<Dog>()
    val selectedDog: SharedFlow<Dog> = _selectedDog.asSharedFlow()

    init {
        retrieveDogsList()
    }

    private fun retrieveDogsList() {
        viewModelScope.launch {
            getDogsListUseCase.invoke(BREED).cachedIn(this).collect {
                _dogsList.value = it
            }
        }
    }

    fun onDogSelected(dog: Dog) {
        viewModelScope.launch {
            _selectedDog.emit(dog)
        }
    }
}

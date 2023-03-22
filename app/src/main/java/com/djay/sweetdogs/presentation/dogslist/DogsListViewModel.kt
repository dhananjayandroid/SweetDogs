package com.djay.sweetdogs.presentation.dogslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djay.sweetdogs.common.Result
import com.djay.sweetdogs.domain.model.Dog
import com.djay.sweetdogs.domain.usecases.GetDogsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogsListViewModel @Inject constructor(
    private val getDogsListUseCase: GetDogsListUseCase
) : ViewModel() {

    private val _dogsList = MutableStateFlow<List<Dog>>(emptyList())
    var dogsList: StateFlow<List<Dog>> = _dogsList.asStateFlow()
    private val _selectedDog = MutableSharedFlow<Dog>()
    val selectedDog: SharedFlow<Dog> = _selectedDog.asSharedFlow()

    init {
        retrieveDogsList()
    }

    private fun retrieveDogsList() {
        viewModelScope.launch {
            getDogsListUseCase.invoke(BREED).collect {
                when(it) {
                    is Result.Success ->  _dogsList.value = it.data
                    is Result.Error ->  _dogsList.value = emptyList()
                }

            }
        }
    }

    fun onDogSelected(dog: Dog) {
        viewModelScope.launch {
            _selectedDog.emit(dog)
        }
    }

    private companion object {
        const val BREED = 0
    }
}

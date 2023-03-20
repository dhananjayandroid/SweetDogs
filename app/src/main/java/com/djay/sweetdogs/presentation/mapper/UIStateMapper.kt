package com.djay.sweetdogs.presentation.mapper

import androidx.paging.PagingData
import com.djay.sweetdogs.common.CallErrors
import com.djay.sweetdogs.common.Result
import com.djay.sweetdogs.domain.model.Dog
import com.djay.sweetdogs.presentation.state.UIState
import kotlinx.coroutines.flow.*

suspend fun Flow<Result<PagingData<Dog>>>.mapResultToDogsUIStateFlow(): Flow<UIState<PagingData<Dog>>> {
    return this.map {
        when (it) {
            is Result.Success -> UIState.SuccessWithData(it.data)
            is Result.Error -> UIState.Error(it.exception)
        }
    }.asFlowWithResult()
}

suspend fun <T> Flow<UIState<T>>.asFlowWithResult(): Flow<UIState<T>> {
    return this
        .onStart { emit(UIState.Loading(true)) }
        .onCompletion { emit(UIState.Loading(false)) }
        .catch { emit(UIState.Error(CallErrors.ErrorException(it))) }
}
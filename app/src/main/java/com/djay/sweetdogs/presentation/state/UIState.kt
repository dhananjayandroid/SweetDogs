package com.djay.sweetdogs.presentation.state

import com.djay.sweetdogs.common.CallErrors

sealed interface UIState<out T> {
    data class SuccessWithData<T>(val data: T) : UIState<T>
    data class Loading(val isLoading: Boolean) : UIState<Nothing>
    data class Error(val callErrors: CallErrors) : UIState<Nothing>
    object SuccessWithNoData : UIState<Nothing>
}
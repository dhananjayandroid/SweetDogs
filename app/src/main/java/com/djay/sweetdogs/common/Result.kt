package com.djay.sweetdogs.common

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: CallErrors) : Result<Nothing>()
}
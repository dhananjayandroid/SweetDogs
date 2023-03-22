package com.djay.sweetdogs.data.repository

import com.djay.sweetdogs.common.CallErrors
import com.djay.sweetdogs.common.Result
import retrofit2.Response

class ResultHandler {

    fun <T, R : Any> handleResult(result: Response<T>, transform: (T) -> R): Result<R> {
        return try {
            if (result.isSuccessful) {
                val resultBody = result.body()
                if (resultBody != null) {
                    Result.Success(transform(resultBody))
                } else {
                    Result.Error(CallErrors.ErrorEmptyData)
                }
            } else {
                Result.Error(CallErrors.ErrorException(Exception("Code: ${result.code()} - Error: ${result.message()}")))
            }
        } catch (e: Exception) {
            Result.Error(CallErrors.ErrorException(e))
        }
    }
}
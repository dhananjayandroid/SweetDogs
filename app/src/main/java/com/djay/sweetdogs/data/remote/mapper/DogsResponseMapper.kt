package com.djay.sweetdogs.data.remote.mapper

import com.djay.sweetdogs.data.remote.model.DogDTO
import com.djay.sweetdogs.common.CallErrors
import com.djay.sweetdogs.common.Result
import retrofit2.Response
import javax.inject.Inject

class DogsResponseMapper @Inject constructor() : ResponseMapper<List<DogDTO>> {
    override fun map(response: Response<List<DogDTO>>): Result<List<DogDTO>> {
        return try {
            if (response.isSuccessful) {
                val resultBody = response.body()
                if (resultBody != null) {
                    Result.Success(resultBody)
                } else {
                    Result.Error(CallErrors.ErrorEmptyData)
                }
            } else {
                Result.Error(CallErrors.ErrorException(Exception("Code: ${response.code()} - Error: ${response.message()}")))
            }


        } catch (e: Exception) {
            Result.Error(CallErrors.ErrorException(e))
        }
    }
}

package com.djay.sweetdogs.data.repository

import com.djay.sweetdogs.data.mapper.DogMapper
import com.djay.sweetdogs.data.model.DogResponse
import com.djay.sweetdogs.data.remote.api.DogsService
import com.djay.sweetdogs.domain.common.CallErrors
import com.djay.sweetdogs.domain.common.Result
import com.djay.sweetdogs.domain.model.Dog
import com.djay.sweetdogs.domain.repository.DogsRepository
import retrofit2.Response
import javax.inject.Inject

class DogsRepositoryImpl @Inject constructor(private val dogsService: DogsService) :
    DogsRepository {

    private fun getResult(result: Response<List<DogResponse>>): Result<List<Dog>> {
        return try {
            if (result.isSuccessful) {
                val resultBody = result.body()
                if (resultBody != null) {
                    Result.Success(DogMapper().mapFromEntityList(resultBody))
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

    override suspend fun getDogs(pageSize: Int, pageNumber: Int, breed: Int): Result<List<Dog>> {
        return try {
            getResult(dogsService.getDogsResponse(pageSize, pageNumber, breed))
        } catch (e: Exception) {
            Result.Error(CallErrors.ErrorException(e))
        }
    }
}

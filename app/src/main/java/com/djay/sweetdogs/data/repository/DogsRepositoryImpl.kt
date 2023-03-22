package com.djay.sweetdogs.data.repository

import com.djay.sweetdogs.common.CallErrors
import com.djay.sweetdogs.common.Result
import com.djay.sweetdogs.data.remote.api.DogsService
import com.djay.sweetdogs.data.remote.model.toDogList
import com.djay.sweetdogs.domain.model.Dog
import com.djay.sweetdogs.domain.repository.DogsRepository
import javax.inject.Inject

class DogsRepositoryImpl @Inject constructor(
    private val dogsService: DogsService,
    private val resultHandler: ResultHandler
) :
    DogsRepository {

    override suspend fun getDogs(pageSize: Int, pageNumber: Int, breed: Int): Result<List<Dog>> {
        return try {
            resultHandler.handleResult(
                dogsService.getDogsResponse(
                    pageSize,
                    pageNumber,
                    breed
                )
            ) { it.toDogList() }
        } catch (e: Exception) {
            Result.Error(CallErrors.ErrorException(e))
        }
    }
}

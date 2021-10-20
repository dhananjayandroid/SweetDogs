package com.djay.sweetdogs.domain.repository


import com.djay.sweetdogs.domain.common.Result
import com.djay.sweetdogs.domain.model.Dog

interface DogsRepository {
    suspend fun getDogs(
        pageSize: Int,
        pageNumber: Int,
        breed: Int
    ): Result<List<Dog>>
}

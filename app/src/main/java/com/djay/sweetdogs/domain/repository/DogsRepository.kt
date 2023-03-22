package com.djay.sweetdogs.domain.repository


import com.djay.sweetdogs.common.Result
import com.djay.sweetdogs.domain.model.Dog

interface DogsRepository {
    suspend fun getDogs(
        breed: Int
    ): Result<List<Dog>>
}

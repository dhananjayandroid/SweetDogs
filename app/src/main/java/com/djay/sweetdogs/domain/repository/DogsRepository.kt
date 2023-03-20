package com.djay.sweetdogs.domain.repository


import androidx.paging.PagingData
import com.djay.sweetdogs.common.Result
import com.djay.sweetdogs.domain.model.Dog
import kotlinx.coroutines.flow.Flow

interface DogsRepository {
    suspend fun getDogs(
        pageSize: Int,
        pageNumber: Int,
        breed: Int
    ): Flow<Result<PagingData<Dog>>>
}

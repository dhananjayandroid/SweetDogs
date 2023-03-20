package com.djay.sweetdogs.data.repository

import androidx.paging.PagingData
import com.djay.sweetdogs.common.Result
import com.djay.sweetdogs.domain.model.Dog
import com.djay.sweetdogs.domain.repository.DogsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DogsRepositoryImpl @Inject constructor(
    private val dogsRepositoryDelegate: DogsRepositoryDelegate
) :
    DogsRepository {

    override suspend fun getDogs(
        pageSize: Int,
        pageNumber: Int,
        breed: Int
    ): Flow<Result<PagingData<Dog>>> = dogsRepositoryDelegate.getDogs(pageSize, pageNumber, breed)
}

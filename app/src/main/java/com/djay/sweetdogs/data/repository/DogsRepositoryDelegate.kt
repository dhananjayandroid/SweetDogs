package com.djay.sweetdogs.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.djay.sweetdogs.common.CallErrors
import com.djay.sweetdogs.common.Result
import com.djay.sweetdogs.data.remote.api.DogsService
import com.djay.sweetdogs.data.remote.mapper.ResponseMapper
import com.djay.sweetdogs.data.remote.model.DogDTO
import com.djay.sweetdogs.data.utils.toDogList
import com.djay.sweetdogs.domain.model.Dog
import com.djay.sweetdogs.domain.paging.DogPagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DogsRepositoryDelegate @Inject constructor(
    private val dogsService: DogsService,
    private val mapper: ResponseMapper<List<DogDTO>>,
    private val coroutineScope: CoroutineScope
) {
    suspend fun getDogs(
        pageSize: Int,
        pageNumber: Int,
        breed: Int
    ): Flow<Result<PagingData<Dog>>> {
        return try {
            val response = dogsService.getDogsResponse(pageSize, pageNumber, breed)
            val result = mapper.map(response)
            if (result is Result.Success)
                Pager(
                    config = PagingConfig(
                        pageSize = DogPagingSource.PAGE_SIZE,
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = {
                        DogPagingSource { pageNumber, pageSize ->
                            (mapper.map(
                                dogsService.getDogsResponse(
                                    pageSize,
                                    pageNumber,
                                    DogPagingSource.BREED
                                )
                            ) as Result.Success).data.toDogList()
                        }
                    }
                ).flow.cachedIn(coroutineScope).map { Result.Success(it) }
            else
                flowOf(Result.Error(CallErrors.ErrorEmptyData))
        } catch (e: Exception) {
            flowOf(Result.Error(CallErrors.ErrorException(e)))
        }
    }
}

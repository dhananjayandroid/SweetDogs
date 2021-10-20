package com.djay.sweetdogs.data.pagination

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.djay.sweetdogs.data.pagination.DogPagingSource.Constants.BREED
import com.djay.sweetdogs.data.pagination.DogPagingSource.Constants.PAGE_SIZE
import com.djay.sweetdogs.domain.model.Dog
import com.djay.sweetdogs.domain.usecases.GetDogsListUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DogDataSourceFactory @Inject constructor(
    private val getDogListUseCase: GetDogsListUseCase,
) {
    suspend fun getDogList(): Flow<PagingData<Dog>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                DogPagingSource { pageNumber, pageSize ->
                    getDogListUseCase(
                        GetDogsListUseCase.Params(pageSize, pageNumber, BREED)
                    )
                }
            }
        ).flow
    }

}
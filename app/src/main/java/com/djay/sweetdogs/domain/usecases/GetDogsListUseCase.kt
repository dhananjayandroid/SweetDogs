package com.djay.sweetdogs.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.djay.sweetdogs.domain.model.Dog
import com.djay.sweetdogs.domain.paging.DogPagingSource
import com.djay.sweetdogs.domain.paging.PagingConstants.Companion.PAGE_SIZE
import com.djay.sweetdogs.domain.repository.DogsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDogsListUseCase @Inject constructor(private val dogsRepository: DogsRepository) :
    suspend (Int) -> Flow<PagingData<Dog>> {

    override suspend operator fun invoke(breed: Int) =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                DogPagingSource { pageNumber, pageSize ->
                    dogsRepository.getDogs(pageSize, pageNumber, breed)
                }
            }
        ).flow
}

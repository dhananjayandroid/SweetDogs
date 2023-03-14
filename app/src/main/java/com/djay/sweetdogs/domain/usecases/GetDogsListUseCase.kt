package com.djay.sweetdogs.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.djay.sweetdogs.domain.model.Dog
import com.djay.sweetdogs.domain.paging.DogPagingSource
import com.djay.sweetdogs.domain.repository.DogsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias GetDogsListBaseUseCase = BaseUseCase<GetDogsListUseCase.Params, Flow<PagingData<Dog>>>

class GetDogsListUseCase @Inject constructor(
    private val dogsRepository: DogsRepository
) : GetDogsListBaseUseCase {

    override suspend operator fun invoke(params: Params) =
        Pager(
            config = PagingConfig(pageSize = DogPagingSource.Constants.PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                DogPagingSource { pageNumber, pageSize ->
                    dogsRepository.getDogs(pageSize, pageNumber, DogPagingSource.Constants.BREED)
                }
            }
        ).flow
    data class Params(val pageSize: Int, val breed: Int)
}

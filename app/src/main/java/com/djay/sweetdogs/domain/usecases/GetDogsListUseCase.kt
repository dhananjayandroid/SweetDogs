package com.djay.sweetdogs.domain.usecases

import androidx.paging.PagingData
import com.djay.sweetdogs.common.Result
import com.djay.sweetdogs.domain.model.Dog
import com.djay.sweetdogs.domain.paging.DogPagingSource
import com.djay.sweetdogs.domain.repository.DogsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias GetDogsListBaseUseCase = BaseUseCase<GetDogsListUseCase.Params, Flow<Result<PagingData<Dog>>>>

class GetDogsListUseCase @Inject constructor(
    private val dogsRepository: DogsRepository
) : GetDogsListBaseUseCase {

    override suspend operator fun invoke(params: Params) =
        dogsRepository.getDogs(params.pageSize, 1, DogPagingSource.BREED)

    data class Params(val pageSize: Int, val breed: Int)
}

package com.djay.sweetdogs.domain.usecases

import com.djay.sweetdogs.domain.common.Result
import com.djay.sweetdogs.domain.model.Dog
import com.djay.sweetdogs.domain.repository.DogsRepository
import javax.inject.Inject

typealias GetDogsListBaseUseCase = BaseUseCase<GetDogsListUseCase.Params, Result<List<Dog>>>

class GetDogsListUseCase @Inject constructor(
    private val dogsRepository: DogsRepository
) : GetDogsListBaseUseCase {

    override suspend operator fun invoke(params: Params) =
        dogsRepository.getDogs(
            pageSize = params.pageSize,
            pageNumber = params.pageNumber,
            breed = params.breed
        )

    data class Params(val pageSize: Int, val pageNumber: Int, val breed: Int)
}

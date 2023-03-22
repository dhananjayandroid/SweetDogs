package com.djay.sweetdogs.domain.usecases

import com.djay.sweetdogs.common.Result
import com.djay.sweetdogs.domain.model.Dog
import com.djay.sweetdogs.domain.repository.DogsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetDogsListUseCase @Inject constructor(private val dogsRepository: DogsRepository) :
    suspend (Int) -> Flow<Result<List<Dog>>> {

    override suspend operator fun invoke(breed: Int) = flowOf(dogsRepository.getDogs(breed))
}

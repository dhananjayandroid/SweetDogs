package com.djay.sweetdogs.domain.usercases

import com.djay.sweetdogs.FakeDataProvider
import com.djay.sweetdogs.data.mapper.DogMapper
import com.djay.sweetdogs.domain.common.Result
import com.djay.sweetdogs.domain.repository.DogsRepository
import com.djay.sweetdogs.domain.usecases.GetDogsListUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetDogsListUseCaseTest {

    private lateinit var dogsRepository: DogsRepository

    private lateinit var useCase: GetDogsListUseCase

    @Before
    fun setUp() {
        dogsRepository = mockk()
        useCase = GetDogsListUseCase(dogsRepository)
    }

    @Test
    fun `should return flow`() = runBlocking {

        val pageSize = 30
        val breed = 1
        val params = GetDogsListUseCase.Params(pageSize, breed)

        val fakeDogList = DogMapper().mapFromEntityList(
            listOf(
                FakeDataProvider.fakeDogResponse1,
                FakeDataProvider.fakeDogResponse2
            )
        )

        coEvery { dogsRepository.getDogs(pageSize, 1, breed) } returns Result.Success(fakeDogList)

        val resultFlow = useCase(params)

        assertTrue(resultFlow is Flow<*>)
    }

}

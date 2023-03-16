package com.djay.sweetdogs.domain.usercases

import com.djay.sweetdogs.domain.common.Result
import com.djay.sweetdogs.domain.repository.DogsRepository
import com.djay.sweetdogs.domain.usecases.GetDogsListUseCase
import com.djay.sweetdogs.utils.FakeDataProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
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
    fun `should return data`() = runBlocking {

        val pageSize = 30
        val breed = 1
        val params = mockk<GetDogsListUseCase.Params>()

        val fakeDogList = FakeDataProvider.fakeDogsList

        coEvery { dogsRepository.getDogs(pageSize, 1, breed) } returns Result.Success(fakeDogList)

        val resultFlow = useCase(params)

        assertNotNull(resultFlow)
    }

}

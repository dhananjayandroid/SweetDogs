package com.djay.sweetdogs.domain.usercases

import app.cash.turbine.test
import com.djay.sweetdogs.common.CallErrors
import com.djay.sweetdogs.common.Result
import com.djay.sweetdogs.domain.repository.DogsRepository
import com.djay.sweetdogs.domain.usecases.GetDogsListUseCase
import com.djay.sweetdogs.utils.FakeDataProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class GetDogsListUseCaseTest {

    private val dogsRepository: DogsRepository = mockk()

    private var useCase = GetDogsListUseCase(dogsRepository)

    @Test
    fun `Given valid parameters, when useCase is called, then return success as result`() {
        runTest {
            // Given
            val breed = 0
            val expected = Result.Success(FakeDataProvider.fakeDogsList)

            coEvery { dogsRepository.getDogs(breed) } returns expected

            // When
            val resultFlow = useCase(breed)

            // Then
            resultFlow.test {
                assertEquals(expected, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `Given invalid parameters, when useCase is called, then return exception as result`() {
        runTest {
            // Given
            val breed = -1
            val expected = Result.Error(CallErrors.ErrorException(Throwable("Invalid Request")))

            coEvery { dogsRepository.getDogs(breed) } returns expected

            // When
            val resultFlow = useCase(breed)

            // Then
            resultFlow.test {
                assertEquals(expected, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}

package com.djay.sweetdogs.domain.usercases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import app.cash.turbine.test
import com.djay.sweetdogs.common.CallErrors
import com.djay.sweetdogs.common.Result
import com.djay.sweetdogs.domain.paging.DogPagingSource
import com.djay.sweetdogs.domain.paging.FakeDogPagingSource
import com.djay.sweetdogs.domain.repository.DogsRepository
import com.djay.sweetdogs.domain.usecases.GetDogsListUseCase
import com.djay.sweetdogs.utils.FakeDataProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class GetDogsListUseCaseTest {

    private val dogsRepository: DogsRepository = mockk()

    var useCase = GetDogsListUseCase(dogsRepository)

    @Test
    fun `Given valid parameters, when getDogs is called, then return success result`() {
        runTest {
            // Given
            val pageSize = 30
            val breed = DogPagingSource.BREED
            val params = GetDogsListUseCase.Params(pageSize, breed)
            val pager = Pager(PagingConfig(pageSize)) {
                FakeDogPagingSource(FakeDataProvider.fakeDogsList)
            }.flow.map { Result.Success(it) }

            coEvery { dogsRepository.getDogs(pageSize, 1, breed) } returns pager

            // When
            val resultFlow = useCase(params)

            // Then
            resultFlow.test {
                assertThat(
                    awaitItem(),
                    CoreMatchers.instanceOf(Result.Success::class.java)
                )
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `Given error occurs, when getDogs is called, then return error result`() {
        runTest {
            // Given
            val pageSize = 30
            val breed = DogPagingSource.BREED
            val params = GetDogsListUseCase.Params(pageSize, breed)
            val error = Exception("Code: 400 - Error: Bad Request")
            val expected = Result.Error(CallErrors.ErrorException(error))

            coEvery { dogsRepository.getDogs(pageSize, 1, breed) } returns flowOf(expected)

            // When
            val resultFlow = useCase(params)

            // Then
            resultFlow.test {
                val item = awaitItem()
                assertThat(item, CoreMatchers.instanceOf(Result.Error::class.java))
                assertEquals(expected, item)
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}

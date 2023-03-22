package com.djay.sweetdogs.domain.usercases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.cash.turbine.test
import com.djay.sweetdogs.common.CallErrors
import com.djay.sweetdogs.common.Result
import com.djay.sweetdogs.domain.paging.DogPagingSource
import com.djay.sweetdogs.domain.paging.FakeDogPagingSource
import com.djay.sweetdogs.domain.paging.PagingConstants.Companion.BREED
import com.djay.sweetdogs.domain.paging.PagingConstants.Companion.PAGE_SIZE
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
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class GetDogsListUseCaseTest {

    private val dogsRepository: DogsRepository = mockk()

    var useCase = GetDogsListUseCase(dogsRepository)

    @Test
    fun `Given valid parameters, when useCase is called, then return PagingData as result`() {
        runTest {
            // Given
            val pageSize = PAGE_SIZE
            val breed = BREED
            val result = Result.Success(FakeDataProvider.fakeDogsList)

            coEvery { dogsRepository.getDogs(pageSize, 1, breed) } returns result

            // When
            val resultFlow = useCase(breed)

            // Then
            resultFlow.test {
                assertThat(
                    awaitItem(),
                    instanceOf(PagingData::class.java)
                )
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}

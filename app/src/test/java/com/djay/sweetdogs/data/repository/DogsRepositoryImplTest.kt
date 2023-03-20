package com.djay.sweetdogs.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import app.cash.turbine.test
import com.djay.sweetdogs.common.CallErrors
import com.djay.sweetdogs.common.Result
import com.djay.sweetdogs.domain.paging.FakeDogPagingSource
import com.djay.sweetdogs.utils.FakeDataProvider
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DogsRepositoryImplTest {

    private val dogsRepositoryDelegate: DogsRepositoryDelegate = mockk()
    private val dogsRepository: DogsRepositoryImpl = DogsRepositoryImpl(dogsRepositoryDelegate)

    @Test
    fun `Given valid parameters, when getDogs is called, then return success result`() {
        runTest {
            // Given
            val pageSize = 10
            val pageNumber = 1
            val breed = 1
            val pager = Pager(PagingConfig(pageSize)) {
                FakeDogPagingSource(FakeDataProvider.fakeDogsList)
            }.flow.map { Result.Success(it) }

            coEvery { dogsRepositoryDelegate.getDogs(pageSize, pageNumber, breed) } returns pager

            // When
            val result = dogsRepository.getDogs(pageSize, pageNumber, breed)

            // Then
            result.test {
                assertThat(awaitItem(), instanceOf(Result.Success::class.java))
                cancelAndConsumeRemainingEvents()
            }
        }
    }


    @Test
    fun `given invalid parameters, when getDogs is called, then an error should be returned`() {
        runTest {
            // Given
            val pageSize = 20
            val pageNumber = -1
            val breed = 1
            val error = Exception("Code: 400 - Error: Bad Request")
            val expected = Result.Error(CallErrors.ErrorException(error))

            coEvery { dogsRepositoryDelegate.getDogs(pageSize, pageNumber, breed) } returns flowOf(
                expected
            )

            // When
            val result = dogsRepository.getDogs(pageSize, pageNumber, breed)

            // Then
            result.test {
                val item = awaitItem()
                assertThat(item, instanceOf(Result.Error::class.java))
                assertEquals(expected, item)
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}
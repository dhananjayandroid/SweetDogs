package com.djay.sweetdogs.data.repository

import com.djay.sweetdogs.data.remote.api.DogsService
import com.djay.sweetdogs.data.remote.model.DogDTO
import com.djay.sweetdogs.domain.model.Dog
import com.djay.sweetdogs.utils.FakeDataProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.any
import org.junit.Test
import com.djay.sweetdogs.common.Result
import io.mockk.every
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class DogsRepositoryImplTest {

    private val dogsService: DogsService = mockk()
    private val resultHandler: ResultHandler = mockk()
    private val dogsRepository: DogsRepositoryImpl = DogsRepositoryImpl(dogsService, resultHandler)

    @Test
    fun `Given valid parameters, when getDogs is called, then return success result`() {
//        runTest {
//            //  Given
//            val breed = 1
//            val callbackMock: (List<DogDTO>) -> List<Dog> = mockk()
//            val mockResult: Response<List<DogDTO>> = Response.success(FakeDataProvider.fakeDogsResponseList)
//            val expectedDogsList = FakeDataProvider.fakeDogsList
//            val mockDogsResponse = Result.Success(expectedDogsList)
//
//            coEvery { dogsService.getDogsResponse(breed) } returns mockResult
//            coEvery { resultHandler.handleResult(any(), callbackMock) } returns mockDogsResponse
//
//            //  When
//            val result = dogsRepository.getDogs(breed)
//
//            // Then
//            assertThat(result, instanceOf(Result.Success::class.java))
//        }
    }


//    @Test
//    fun `given invalid parameters, when getDogs is called, then an error should be returned`() {
//        runTest {
//            // Given
//            val pageSize = 20
//            val pageNumber = -1
//            val breed = 1
//            val error = Exception("Code: 400 - Error: Bad Request")
//            val expected = Result.Error(CallErrors.ErrorException(error))
//
//            coEvery { dogsRepositoryDelegate.getDogs(pageSize, pageNumber, breed) } returns flowOf(
//                expected
//            )
//
//            // When
//            val result = dogsRepository.getDogs(pageSize, pageNumber, breed)
//
//            // Then
//            result.test {
//                val item = awaitItem()
//                assertThat(item, instanceOf(Result.Error::class.java))
//                assertEquals(expected, item)
//                cancelAndConsumeRemainingEvents()
//            }
//        }
//    }
}
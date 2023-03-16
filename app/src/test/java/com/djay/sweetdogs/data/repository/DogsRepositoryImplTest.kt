package com.djay.sweetdogs.data.repository

import com.djay.sweetdogs.utils.FakeDataProvider
import com.djay.sweetdogs.data.mapper.DogMapper
import com.djay.sweetdogs.data.model.DogResponse
import com.djay.sweetdogs.data.remote.api.DogsService
import com.djay.sweetdogs.domain.common.CallErrors
import com.djay.sweetdogs.domain.common.Result
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

@Suppress("DEPRECATION")
@OptIn(ExperimentalCoroutinesApi::class)
class DogsRepositoryImplTest {
    private val dogsService: DogsService = mockk()
    private val dogsRepository: DogsRepositoryImpl = DogsRepositoryImpl(dogsService)

    @Test
    fun `getDogs returns Success`() = runBlockingTest {
        // Given
        val pageSize = 10
        val pageNumber = 1
        val breed = 1
        val response: Response<List<DogResponse>> = mockk()
        val dogResponses = FakeDataProvider.fakeDogsResponseList
        val fakeDogsList = DogMapper().mapFromEntityList(dogResponses)
        coEvery { dogsService.getDogsResponse(pageSize, pageNumber, breed) } returns response
        every { response.isSuccessful } returns true
        every { response.body() } returns dogResponses

        // When
        val result = dogsRepository.getDogs(pageSize, pageNumber, breed)

        // Then
        assertEquals(Result.Success(fakeDogsList), result)
    }


    @Test
    fun `getDogs throws Exception`() = runBlockingTest {
        // Arrange
        val pageSize = 20
        val pageNumber = 1
        val breed = 1
        val error = Exception("Network Error")
        coEvery { dogsService.getDogsResponse(pageSize, pageNumber, breed) } throws error
        val expected = Result.Error(CallErrors.ErrorException(error))
        // Act
        val result = dogsRepository.getDogs(pageSize, pageNumber, breed)
        // Assert
        assertEquals(expected, result)
    }


}
package com.djay.sweetdogs.presentation.dogslist

import app.cash.turbine.test
import com.djay.sweetdogs.common.CallErrors
import com.djay.sweetdogs.common.Result
import com.djay.sweetdogs.domain.usecases.GetDogsListUseCase
import com.djay.sweetdogs.utils.FakeDataProvider
import com.djay.sweetdogs.utils.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DogsListViewModelTest {

    private val getDogsListUseCase: GetDogsListUseCase = mockk()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `given valid params, when retrieveDogsList is called, then dogsList should contain data`() {
        runTest {
            // GIVEN
            val expectedList = FakeDataProvider.fakeDogsList
            val resultFlow = flowOf(Result.Success(expectedList))
            coEvery { getDogsListUseCase.invoke(any()) } returns resultFlow

            // When
            val dogsListViewModel = DogsListViewModel(getDogsListUseCase)

            // Then
            dogsListViewModel.dogsList.test {
                assertEquals(0, awaitItem().size)
                assertEquals(expectedList, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `given invalid params, when retrieveDogsList is called, then dogsList should not contain data`() {
        runTest {
            // GIVEN
            val resultFlow =
                flowOf(Result.Error(CallErrors.ErrorException(Throwable("Invalid Request"))))
            coEvery { getDogsListUseCase.invoke(any()) } returns resultFlow

            // When
            val dogsListViewModel = DogsListViewModel(getDogsListUseCase)

            // Then
            dogsListViewModel.dogsList.test {
                assertTrue(awaitItem().isEmpty())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `given a dog is selected, when onDogSelected is called, then selectedDog return selected dog`() {
        runTest {
            // GIVEN
            val expected = FakeDataProvider.fakeDog

            // When
            val dogsListViewModel = DogsListViewModel(getDogsListUseCase)
            dogsListViewModel.onDogSelected(expected)

            // Then
            dogsListViewModel.selectedDog.test {
                assertEquals(expected, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}
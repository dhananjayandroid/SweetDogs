package com.djay.sweetdogs.presentation.dogslist

import androidx.paging.Pager
import androidx.paging.PagingConfig
import app.cash.turbine.test
import com.djay.sweetdogs.domain.paging.FakeDogPagingSource
import com.djay.sweetdogs.domain.usecases.GetDogsListUseCase
import com.djay.sweetdogs.presentation.utils.CoroutineContextProvider
import com.djay.sweetdogs.utils.TestDispatcherProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DogsListViewModelTest {

    // Use Mockk instead of Mockito for mocking objects
    @MockK
    lateinit var getDogsListUseCase: GetDogsListUseCase

    private lateinit var coroutineContextProvider: CoroutineContextProvider

    // Create an instance of the ViewModel
    private lateinit var dogsListViewModel: DogsListViewModel

    @Before
    fun setup() {
        // Initialize Mockk
        MockKAnnotations.init(this)

        // Create a new instance of the ViewModel with the mocked use case
        coroutineContextProvider = TestDispatcherProvider()
        dogsListViewModel = DogsListViewModel(coroutineContextProvider, getDogsListUseCase)
    }

    @Test
    fun `test get dogs list`() {
        runTest {
            val pager = Pager(PagingConfig(pageSize = 20)) {
                FakeDogPagingSource(any())
            }

            coEvery { getDogsListUseCase.invoke(any()) } returns pager.flow

            dogsListViewModel.dogsList.test {
                assertNotNull(awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

}
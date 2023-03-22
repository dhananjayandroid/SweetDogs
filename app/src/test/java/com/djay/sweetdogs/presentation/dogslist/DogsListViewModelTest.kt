package com.djay.sweetdogs.presentation.dogslist

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.cash.turbine.test
import com.djay.sweetdogs.domain.paging.FakeDogPagingSource
import com.djay.sweetdogs.domain.usecases.GetDogsListUseCase
import com.djay.sweetdogs.utils.FakeDataProvider
import com.djay.sweetdogs.utils.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
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
            val pager = Pager(PagingConfig(pageSize = 20)) {
                FakeDogPagingSource(FakeDataProvider.fakeDogsList)
            }.flow
            coEvery { getDogsListUseCase.invoke(any()) } returns pager

            // When
           val dogsListViewModel = DogsListViewModel(getDogsListUseCase)

            // Then
            dogsListViewModel.dogsList.test {
                assertThat(awaitItem(), instanceOf(PagingData::class.java))
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}
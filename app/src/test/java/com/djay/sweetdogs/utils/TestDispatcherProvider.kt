package com.djay.sweetdogs.utils

import com.djay.sweetdogs.presentation.utils.CoroutineContextProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider : CoroutineContextProvider {

    private val testDispatcher = UnconfinedTestDispatcher()

    override val io = testDispatcher
    override val default = testDispatcher
    override val main: CoroutineDispatcher = testDispatcher
}
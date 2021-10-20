package com.djay.sweetdogs.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djay.sweetdogs.utils.CoroutineContextProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Suppress("unused")
abstract class BaseViewModel(private val contextProvider: CoroutineContextProvider) : ViewModel() {

    private val job: Job = Job()

    protected fun launchCoroutineIO(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(contextProvider.io + job) {
            block()
        }
    }

    protected fun launchCoroutineMain(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(contextProvider.main + job) {
            block()
        }
    }

    public override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}

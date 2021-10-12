package com.yusufarisoy.core.utils

import androidx.lifecycle.ViewModel
import com.yusufarisoy.common.StateError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class StatefulViewModel<S : UiState>(initialState: S) : ViewModel() {

    private val _stateFlow = MutableStateFlow(State(initialState))
    val stateFlow: StateFlow<State<S>>
        get() = _stateFlow

    val currentUiState: S
        get() = _stateFlow.value.uiState

    protected fun setState(reducer: S.() -> S) {
        val newState = _stateFlow.value.copy(uiState = reducer(currentUiState))
        _stateFlow.value = newState
    }

    protected fun setProgress(progress: Boolean) {
        _stateFlow.value = _stateFlow.value.copy(progress = progress)
    }

    protected fun setError(error: StateError?) {
        _stateFlow.value = _stateFlow.value.copy(error = error)
        _stateFlow.value = _stateFlow.value.copy(error = null)
    }
}

data class State<T : UiState>(
    val uiState: T,
    val progress: Boolean = false,
    val error: StateError? = null
)

interface UiState
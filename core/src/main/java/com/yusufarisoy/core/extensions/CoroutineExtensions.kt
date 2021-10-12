package com.yusufarisoy.core.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.yusufarisoy.common.StateError
import com.yusufarisoy.core.utils.StatefulViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun <V : StatefulViewModel<S>, S> withUiState(
    viewModel: V,
    lifecycleOwner: LifecycleOwner,
    isDistinctUntilChange: Boolean,
    onStateChanged: suspend (S) -> Unit
) {
    val stateFlow = viewModel.stateFlow.map {
        it.uiState
    }

    if (isDistinctUntilChange) {
        stateFlow.distinctUntilChanged()
    }

    stateFlow
        .onEach(onStateChanged)
        .collectIn(lifecycleOwner)
}

fun <V : StatefulViewModel<*>> withProgress(
    viewModel: V,
    lifecycleOwner: LifecycleOwner,
    onProgressEvent: suspend (Boolean) -> Unit
) {
    viewModel.stateFlow
        .map { it.progress }
        .distinctUntilChanged()
        .onEach(onProgressEvent)
        .collectIn(lifecycleOwner)
}

fun <V : StatefulViewModel<*>> withError(
    viewModel: V,
    lifecycleOwner: LifecycleOwner,
    onErrorEvent: suspend (StateError) -> Unit
) {
    viewModel.stateFlow
        .mapNotNull { it.error }
        .onEach(onErrorEvent)
        .collectIn(lifecycleOwner)
}

@OptIn(InternalCoroutinesApi::class)
fun <T> Flow<T>.collectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    action: suspend CoroutineScope.(T) -> Unit = {}
) = owner.addRepeatingJob(minActiveState) {
    collect {
        action(it)
    }
}

fun LifecycleOwner.addRepeatingJob(
    state: Lifecycle.State,
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit
): Job = lifecycleScope.launch(coroutineContext) {
    lifecycle.repeatOnLifecycle(state, block)
}

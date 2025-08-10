package com.bonial.challengeapp.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * A composable function that observes a Flow as events and triggers a callback when events are emitted.
 * This function respects the lifecycle of the composable and only collects events when the lifecycle is at least in STARTED state.
 *
 * @param T The type of events in the Flow.
 * @param events The Flow of events to observe.
 * @param key1 Optional key to trigger recomposition when changed.
 * @param key2 Optional additional key to trigger recomposition when changed.
 * @param onEvent Callback function that is invoked for each emitted event.
 */
@Composable
fun <T> ObserveAsEvents(
    events: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycle, key1, key2) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                events.collect(onEvent)
            }
        }
    }
}
package com.bonial.challengeapp.brochure.presentation.brochure_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonial.challengeapp.brochure.domain.BrochureDataSource
import com.bonial.challengeapp.brochure.presentation.model.BrochureUI
import com.bonial.challengeapp.brochure.presentation.model.toBrochureUi
import com.bonial.challengeapp.core.domain.util.onError
import com.bonial.challengeapp.core.domain.util.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing the state and handling user actions for the brochure list screen.
 * Communicates with the [BrochureDataSource] to load brochures and updates the UI state accordingly.
 *
 * @property brochureDataSource The data source for retrieving brochure data
 */
class BrochureViewModel(private val brochureDataSource: BrochureDataSource) : ViewModel() {

    /**
     * Internal mutable state flow that holds the current state of the brochure list screen.
     */
    private val _state = MutableStateFlow(BrochureListState())

    /**
     * Brochure list UI state as a [StateFlow].
     * Keeps upstream active for 5s after the last subscriber to
     * avoid unnecessary reloads during quick lifecycle changes
     * (e.g., orientation change). Starts by calling [loadBrochures].
     */

    val state = _state
        .onStart { loadBrochures() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            BrochureListState()
        )

    /**
     * Internal channel for one-time events like errors.
     */
    private val _events = Channel<BrochureListEvent>()
    
    /**
     * Public flow of one-time events that should be handled by the UI.
     */
    val events = _events.receiveAsFlow()

    /**
     * Handles user actions from the UI.
     *
     * @param action The action to handle
     */
    fun onAction(action: BrochureListAction) {
        when (action) {
            is BrochureListAction.OnBrochureClick -> {
                selectBrochure(action.brochureUi)
            }
        }
    }

    /**
     * Updates the state to select a brochure.
     * This is called when a user clicks on a brochure in the list.
     *
     * @param brochureUI The brochure UI model that was selected
     */
    private fun selectBrochure(brochureUI: BrochureUI) {
        _state.update { it.copy(selectedBrochure = brochureUI) }
    }

    /**
     * Loads brochures from the data source and updates the state accordingly.
     * Sets loading state, fetches brochures, and handles success or error cases.
     */
    private fun loadBrochures() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            brochureDataSource
                .getBrochures()
                .onSuccess { brochures ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            brochures = brochures.map { it.toBrochureUi() }
                        )
                    }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    _events.send(BrochureListEvent.Error(error))
                }
        }
    }
}
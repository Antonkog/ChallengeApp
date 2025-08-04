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

class BrochureViewModel(private val brochureDataSource: BrochureDataSource) : ViewModel() {


    private val _state = MutableStateFlow(BrochureListState())
    val state = _state
        .onStart { loadBrochures() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            BrochureListState()
        )

    private val _events = Channel<BrochureListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: BrochureListAction) {
        when (action) {
            is BrochureListAction.OnBrochureClick -> {
                selectBrochure(action.brochureUi)
            }
        }
    }

    private fun selectBrochure(brochureUI: BrochureUI) {
        _state.update { it.copy(selectedBrochure = brochureUI) }
    }

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
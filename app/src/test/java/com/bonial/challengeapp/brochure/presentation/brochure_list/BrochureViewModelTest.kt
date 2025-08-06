package com.bonial.challengeapp.brochure.presentation.brochure_list

import com.bonial.challengeapp.brochure.data.networking.FakeBrochureDataSource
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class BrochureViewModelTest {

    private lateinit var viewModel: BrochureViewModel

    @Before
    fun setUp() {
        viewModel = BrochureViewModel(FakeBrochureDataSource())
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `state is updated with loaded brochures`() = runTest {
        val collectedStates = mutableListOf<BrochureListState>()

        // Start collecting the flow — triggers loadBrochures()
        val job = launch {
            viewModel.state.toList(collectedStates)
        }

        advanceUntilIdle()

        // The last collected state should have loaded brochures
        val lastState = collectedStates.last()

        Truth.assertThat(lastState.isLoading).isFalse()
        Truth.assertThat(lastState.brochures).isNotEmpty()

        job.cancel()
    }

    @Test
    fun `on brochure click, selected brochure is updated`() = runTest {
        val collectedStates = mutableListOf<BrochureListState>()
        val job = launch { viewModel.state.toList(collectedStates) }

        advanceUntilIdle()

        val brochure = collectedStates.last().brochures.first()
        viewModel.onAction(BrochureListAction.OnBrochureClick(brochure))
        advanceUntilIdle()
        // Now the selectedBrochure in the latest state should be updated
        val selected = viewModel.state.value.selectedBrochure
        Truth.assertThat(selected).isEqualTo(brochure)

        job.cancel()
    }
}
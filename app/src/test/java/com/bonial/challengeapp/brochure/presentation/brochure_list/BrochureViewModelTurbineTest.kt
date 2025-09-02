package com.bonial.challengeapp.brochure.presentation.brochure_list

import app.cash.turbine.test
import com.bonial.challengeapp.brochure.data.networking.FakeBrochureDataSource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BrochureViewModelTurbineTest {

    private lateinit var viewModel: BrochureViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = BrochureViewModel(FakeBrochureDataSource())
    }

    @Test
    fun state_emits_loading_then_content() = runTest {
        viewModel.state.test {
            // First emission is initial default state
            val initial = awaitItem()
            assertThat(initial.isLoading).isFalse()

            // OnStart triggers loadBrochures(), expect loading state next
            val loading = awaitItem()
            assertThat(loading.isLoading).isTrue()

            // Then loaded state with brochures
            val loaded = awaitItem()
            assertThat(loaded.isLoading).isFalse()
            assertThat(loaded.brochures).isNotEmpty()

            cancelAndIgnoreRemainingEvents()
        }
        advanceUntilIdle()
    }

    @Test
    fun events_flow_emits_error_when_data_source_fails() = runTest {
        // Create a VM with a failing data source
        val failingVm = BrochureViewModel(FakeFailingBrochureDataSource())

        failingVm.events.test {
            // Start state collection to trigger load
            failingVm.state.test {
                // Drain a few state emissions; we don't assert here
                repeat(3) { awaitItem() }
                cancelAndIgnoreRemainingEvents()
            }

            // Expect a single Error event
            val event = awaitItem()
            assertThat(event).isInstanceOf(BrochureListEvent.Error::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }
}

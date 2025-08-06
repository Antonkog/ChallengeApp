@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.bonial.challengeapp.core.navigation

import android.widget.Toast
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bonial.challengeapp.brochure.presentation.brochure_delails.BrochureDetailScreen
import com.bonial.challengeapp.brochure.presentation.brochure_list.BrochureListAction
import com.bonial.challengeapp.brochure.presentation.brochure_list.BrochureListEvent
import com.bonial.challengeapp.brochure.presentation.brochure_list.BrochureListScreen
import com.bonial.challengeapp.brochure.presentation.brochure_list.BrochureViewModel
import com.bonial.challengeapp.core.presentation.util.ObserveAsEvents
import com.bonial.challengeapp.core.presentation.util.TestTags
import com.bonial.challengeapp.core.presentation.util.toString
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun AdaptiveBrochureListDetailPane(
    modifier: Modifier = Modifier,
    viewModel: BrochureViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope() // ← Get CoroutineScope here
    val context = LocalContext.current
    ObserveAsEvents(events = viewModel.events) { event ->
        when (event) {
            is BrochureListEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane(
                modifier = Modifier.testTag("${TestTags.ADAPTIVE_PANE}_list")
            ) {
                BrochureListScreen(
                    isPreview = false,
                    state = state,
                    onAction = { action ->
                        viewModel.onAction(action)
                        when (action) {
                            is BrochureListAction.OnBrochureClick -> {
                                coroutineScope.launch {
                                    navigator.navigateTo(
                                        pane = ListDetailPaneScaffoldRole.Detail
                                    )
                                }
                            }
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane(
                modifier = Modifier.testTag("${TestTags.ADAPTIVE_PANE}_detail")
            ) {
                BrochureDetailScreen(state = state)
            }
        },
        modifier = modifier.testTag(TestTags.ADAPTIVE_PANE)
    )
}
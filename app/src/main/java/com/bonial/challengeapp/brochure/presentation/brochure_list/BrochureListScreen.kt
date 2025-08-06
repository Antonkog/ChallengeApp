package com.bonial.challengeapp.brochure.presentation.brochure_list

import android.R.attr.checked
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.bonial.challengeapp.R
import com.bonial.challengeapp.brochure.presentation.brochure_list.components.BrochureListItem
import com.bonial.challengeapp.brochure.presentation.brochure_list.components.previewBrochure
import com.bonial.challengeapp.core.presentation.util.TestTags
import com.bonial.challengeapp.core.presentation.util.itemsIndexedWithSpan
import com.bonial.challengeapp.ui.theme.ChallengeAppTheme

@Composable
fun BrochureListScreen(
    isPreview: Boolean,
    state: BrochureListState,
    onAction: (BrochureListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    var filterEnabled by rememberSaveable { mutableStateOf(false) }

    val filteredBrochures = remember(state.brochures, filterEnabled) {
        if (filterEnabled) {
            // Filter out brochures with distance > 5.0 km
            state.brochures.filter { it.distanceKm <= 5.0 }
        } else {
            state.brochures
        }
    }

    val columnCount =
        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2

    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .testTag(TestTags.BROCHURE_LIST_LOADING),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .testTag(TestTags.BROCHURE_LIST_SCREEN)
        ) {

            // Filter toggle
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .testTag(TestTags.BROCHURE_LIST_FILTER_TOGGLE)
            ) {

                Text(text = stringResource(R.string.filter_nearby), modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.onSurface)
                Switch(
                    checked = filterEnabled,
                    onCheckedChange = { filterEnabled = it }
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(columnCount),
                modifier = Modifier
                    .weight(1f)
                    .testTag(TestTags.BROCHURE_LIST_CONTENT),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                itemsIndexedWithSpan(
                    items = filteredBrochures,
                    key = { _, item -> item.hashCode() },
                    span = { _, item ->
                        if (item.isPremium) GridItemSpan(columnCount) else GridItemSpan(
                            1
                        )
                    }
                ) { index, brochureUI ->
                    BrochureListItem(
                        isPreview,
                        brochureUi = brochureUI,
                        onClick = {
                            onAction(BrochureListAction.OnBrochureClick(brochureUI))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("${TestTags.BROCHURE_LIST_ITEM}_$index")
                    )
                }
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun BrochureListScreenPreview() {
    ChallengeAppTheme {
        BrochureListScreen(
            isPreview = true,
            state = BrochureListState(
                brochures = (1..10).map {
                    previewBrochure.copy(publisherName = it.toString())
                }
            ),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            onAction = {}
        )
    }
}
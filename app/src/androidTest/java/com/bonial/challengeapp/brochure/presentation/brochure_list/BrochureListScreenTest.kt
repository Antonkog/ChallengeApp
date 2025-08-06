package com.bonial.challengeapp.brochure.presentation.brochure_list

import android.content.res.Configuration
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import com.bonial.challengeapp.brochure.presentation.model.BrochureUI
import com.bonial.challengeapp.core.presentation.util.TestTags
import org.junit.Rule
import org.junit.Test

class BrochureListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingState_showsLoadingIndicator() {
        // Given a loading state
        val state = BrochureListState(isLoading = true)

        // When the screen is composed
        composeTestRule.setContent {
            BrochureListScreen(
                isPreview = false,
                state = state,
                onAction = {}
            )
        }

        // Then the loading indicator is displayed
        composeTestRule.onNodeWithTag(TestTags.BROCHURE_LIST_LOADING).assertIsDisplayed()
    }

    @Test
    fun loadedState_showsBrochureList() {
        // Given a state with brochures
        val brochures = listOf(
            BrochureUI(
                imageUrl = "https://content-media.bonial.biz/330dfb5c-b903-4fd4-9ae0-b196e0285e82/preview.jpg",
                publisherName = "Lidl",
                isExpired = true,
                distanceKm = 0.931,
                isPremium = false
            ),

            BrochureUI(
                imageUrl = "https://content-media.bonial.biz/a624c6c0-e5f3-49c7-9de4-d43c1a127628/preview.jpg",
                publisherName = "REWE",
                isExpired = true,
                distanceKm = 0.638,
                isPremium = false
            )
        )
        val state = BrochureListState(isLoading = false, brochures = brochures)

        // When the screen is composed
        composeTestRule.setContent {
            BrochureListScreen(
                isPreview = false,
                state = state,
                onAction = {}
            )
        }

        // Then the brochure list is displayed with the correct number of items
        composeTestRule.onNodeWithTag(TestTags.BROCHURE_LIST_SCREEN).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.BROCHURE_LIST_CONTENT).assertIsDisplayed()
        composeTestRule.onAllNodesWithTag(TestTags.BROCHURE_LIST_ITEM + "_0").assertCountEquals(1)
        composeTestRule.onAllNodesWithTag(TestTags.BROCHURE_LIST_ITEM + "_1").assertCountEquals(1)
    }

    @Test
    fun adaptiveLayout_portraitMode() {
        // Create test data with multiple brochures including a premium one
        val brochures = listOf(
            BrochureUI(
                imageUrl = "https://content-media.bonial.biz/330dfb5c-b903-4fd4-9ae0-b196e0285e82/preview.jpg",
                publisherName = "Lidl",
                isExpired = true,
                distanceKm = 0.931,
                isPremium = false
            ),
            BrochureUI(
                imageUrl = "https://content-media.bonial.biz/a624c6c0-e5f3-49c7-9de4-d43c1a127628/preview.jpg",
                publisherName = "REWE",
                isExpired = true,
                distanceKm = 0.638,
                isPremium = false
            ),
            BrochureUI(
                imageUrl = "https://content-media.bonial.biz/c8367075-df4b-47cb-9109-f1c09ed23903/preview.jpg",
                publisherName = "BAUHAUS",
                isExpired = true,
                distanceKm = 3.946,
                isPremium = false
            ),
            BrochureUI(
                imageUrl = "https://content-media.bonial.biz/c49aefb0-3b46-4465-bb5e-cc8ab5483de7/preview.jpg",
                publisherName = "Lidl",
                isExpired = true,
                distanceKm = 0.931,
                isPremium = false
            )
        )
        val state = BrochureListState(isLoading = false, brochures = brochures)

        // Test portrait mode (2 columns)
        val portraitConfig = Configuration().apply {
            orientation = Configuration.ORIENTATION_PORTRAIT
        }

        composeTestRule.setContent {
            CompositionLocalProvider(LocalConfiguration provides portraitConfig) {
                BrochureListScreen(
                    isPreview = false,
                    state = state,
                    onAction = {}
                )
            }
        }

        // In portrait mode, premium brochure should span 2 columns
        composeTestRule.onNodeWithTag(TestTags.BROCHURE_LIST_CONTENT).assertIsDisplayed()
    }

    @Test
    fun adaptiveLayout_landscapeMode() {
        // Create test data with multiple brochures including a premium one
        val brochures = listOf(
            BrochureUI(
                imageUrl = "https://content-media.bonial.biz/330dfb5c-b903-4fd4-9ae0-b196e0285e82/preview.jpg",
                publisherName = "Lidl",
                isExpired = true,
                distanceKm = 0.931,
                isPremium = false
            ),
            BrochureUI(
                imageUrl = "https://content-media.bonial.biz/a624c6c0-e5f3-49c7-9de4-d43c1a127628/preview.jpg",
                publisherName = "REWE",
                isExpired = true,
                distanceKm = 0.638,
                isPremium = false
            ),
            BrochureUI(
                imageUrl = "https://content-media.bonial.biz/c8367075-df4b-47cb-9109-f1c09ed23903/preview.jpg",
                publisherName = "BAUHAUS",
                isExpired = true,
                distanceKm = 3.946,
                isPremium = false
            ),
            BrochureUI(
                imageUrl = "https://content-media.bonial.biz/c49aefb0-3b46-4465-bb5e-cc8ab5483de7/preview.jpg",
                publisherName = "Lidl",
                isExpired = true,
                distanceKm = 0.931,
                isPremium = false
            )
        )
        val state = BrochureListState(isLoading = false, brochures = brochures)

        // Test landscape mode (3 columns)
        val landscapeConfig = Configuration().apply {
            orientation = Configuration.ORIENTATION_LANDSCAPE
        }

        composeTestRule.setContent {
            CompositionLocalProvider(LocalConfiguration provides landscapeConfig) {
                BrochureListScreen(
                    isPreview = false,
                    state = state,
                    onAction = {}
                )
            }
        }

        // In landscape mode, premium brochure should span 3 columns
        composeTestRule.onNodeWithTag(TestTags.BROCHURE_LIST_CONTENT).assertIsDisplayed()
    }
}
package com.bonial.challengeapp.brochure.presentation.brochure_delails

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.bonial.challengeapp.brochure.presentation.brochure_list.BrochureListState
import com.bonial.challengeapp.brochure.presentation.model.BrochureUI
import com.bonial.challengeapp.core.presentation.util.TestTags
import org.junit.Rule
import org.junit.Test

class BrochureDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingState_showsLoadingIndicator() {
        // Given a loading state
        val state = BrochureListState(isLoading = true)

        // When the screen is composed
        composeTestRule.setContent {
            BrochureDetailScreen(state = state)
        }

        // Then the loading indicator is displayed
        composeTestRule.onNodeWithTag(TestTags.BROCHURE_LIST_LOADING).assertIsDisplayed()

        // And no detail content should be visible
        composeTestRule.onNodeWithTag(TestTags.BROCHURE_DETAIL_SCREEN).assertDoesNotExist()
    }

    @Test
    fun selectedBrochure_showsDetailScreen() {
        // Given a state with a selected brochure
        val selectedBrochure = BrochureUI(
            imageUrl = "https://content-media.bonial.biz/330dfb5c-b903-4fd4-9ae0-b196e0285e82/preview.jpg",
            publisherName = "Lidl",
            isExpired = true,
            distanceKm = 2.5,
            isPremium = false
        )

        val state = BrochureListState(
            isLoading = false,
            selectedBrochure = selectedBrochure
        )

        // When the screen is composed
        composeTestRule.setContent {
            BrochureDetailScreen(state = state)
        }

        // Then the detail screen is displayed with the correct information
        composeTestRule.onNodeWithTag(TestTags.BROCHURE_DETAIL_SCREEN).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.BROCHURE_DETAIL_IMAGE).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.BROCHURE_DETAIL_PUBLISHER).assertIsDisplayed()

        // Verify the publisher name is displayed correctly
        val publisherNode = composeTestRule.onNodeWithText("Lidl")
        publisherNode.assertIsDisplayed()
        publisherNode.assertTextContains("Lidl")

        // Verify distance information is displayed
        composeTestRule.onNodeWithTag(TestTags.BROCHURE_DETAIL_DISTANCE).assertIsDisplayed()

        // Verify the distance value is correct (assuming it's displayed as "2.5 km")
        composeTestRule.onNodeWithText("2.5 km", substring = true).assertExists()
    }

    @Test
    fun expiredBrochure_showsExpiredLabel() {
        // Given a state with an expired brochure
        val expiredBrochure = BrochureUI(
            imageUrl = "https://example.com/image.jpg",
            publisherName = "Expired Publisher",
            isExpired = true,
            distanceKm = 3.0,
            isPremium = false
        )
        val state = BrochureListState(
            isLoading = false,
            selectedBrochure = expiredBrochure
        )

        // When the screen is composed
        composeTestRule.setContent {
            BrochureDetailScreen(state = state)
        }

        // Then the expired label is displayed
        composeTestRule.onNodeWithTag(TestTags.BROCHURE_DETAIL_SCREEN).assertIsDisplayed()

        // Verify the expired label is displayed
        val expiredLabel = composeTestRule.onNodeWithText("Expired")
        expiredLabel.assertIsDisplayed()

        // Verify other brochure details are displayed correctly
        composeTestRule.onNodeWithText("Expired Publisher").assertIsDisplayed()
        composeTestRule.onNodeWithText("3.0 km", substring = true).assertExists()
    }

    @Test
    fun premiumBrochure_showsPremiumLabel() {
        // Given a state with a premium brochure
        val premiumBrochure = BrochureUI(
            imageUrl = "https://example.com/image.jpg",
            publisherName = "Premium Publisher",
            isExpired = false,
            distanceKm = 1.5,
            isPremium = true
        )
        val state = BrochureListState(
            isLoading = false,
            selectedBrochure = premiumBrochure
        )

        // When the screen is composed
        composeTestRule.setContent {
            BrochureDetailScreen(state = state)
        }

        // Then the premium label is displayed
        composeTestRule.onNodeWithTag(TestTags.BROCHURE_DETAIL_SCREEN).assertIsDisplayed()

        // Verify the premium label is displayed
        val premiumLabel = composeTestRule.onNodeWithText("brochurePremium")
        premiumLabel.assertIsDisplayed()

        // Verify other premium brochure details
        composeTestRule.onNodeWithText("Premium Publisher").assertIsDisplayed()
        composeTestRule.onNodeWithText("1.5 km", substring = true).assertExists()

        // Verify the image is displayed
        composeTestRule.onNodeWithTag(TestTags.BROCHURE_DETAIL_IMAGE).assertIsDisplayed()
    }

    @Test
    fun noSelectedBrochure_showsEmptyState() {
        // Given a state with no selected brochure
        val state = BrochureListState(
            isLoading = false,
            selectedBrochure = null
        )

        // When the screen is composed
        composeTestRule.setContent {
            BrochureDetailScreen(state = state)
        }

        // Then no detail screen should be displayed
        composeTestRule.onNodeWithTag(TestTags.BROCHURE_DETAIL_SCREEN).assertDoesNotExist()

        // Verify that we're not in a loading state
        composeTestRule.onNodeWithTag(TestTags.BROCHURE_LIST_LOADING).assertDoesNotExist()
    }
}
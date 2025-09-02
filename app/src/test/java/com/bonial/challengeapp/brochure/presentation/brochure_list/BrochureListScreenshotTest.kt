package com.bonial.challengeapp.brochure.presentation.brochure_list

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import io.github.takahirom.roborazzi.RoborazziRule
import io.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class BrochureListScreenshotTest {

    @get:Rule(order = 0)
    val composeRule = createComposeRule()

    // Roborazzi JUnit4 rule will hook Robolectric environment and record/verify image.
    @get:Rule(order = 1)
    val roborazziRule = RoborazziRule(
        // default options: record if baseline not exists, then verify in subsequent runs
    )

    @Test
    fun brochureList_emptyState_screenshot() {
        composeRule.setContent { PreviewContent() }
        composeRule.onRoot().captureRoboImage("brochure_list_empty_state")
    }

    @Composable
    private fun PreviewContent() {
        MaterialTheme {
            BrochureListScreen(
                state = BrochureListState(
                    isLoading = false,
                    brochures = emptyList(),
                    error = null
                ),
                onAction = {}
            )
        }
    }
}

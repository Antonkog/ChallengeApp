package com.bonial.challengeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bonial.challengeapp.brochure.presentation.brochure_list.BrochureListScreen
import com.bonial.challengeapp.brochure.presentation.brochure_list.BrochureListState
import com.bonial.challengeapp.brochure.presentation.model.BrochureUI
import com.bonial.challengeapp.core.navigation.AdaptiveBrochureListDetailPane
import com.bonial.challengeapp.ui.theme.ChallengeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChallengeAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AdaptiveBrochureListDetailPane(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun BrochuresPreview() {
    ChallengeAppTheme {
        val fakeState = BrochureListState(
            brochures = listOf(
                BrochureUI(
                    imageUrl = null,
                    publisherName = "Lidl",
                    isExpired = false,
                    distanceKm = 1.2,
                    isPremium = true
                )
            ),
            isLoading = false
        )

        BrochureListScreen(
            isPreview = true,
            state = fakeState,
            onAction = {}
        )
    }
}
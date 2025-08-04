package com.bonial.challengeapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bonial.challengeapp.brochure.presentation.brochure_list.BrochureListEvent
import com.bonial.challengeapp.brochure.presentation.brochure_list.BrochureListScreen
import com.bonial.challengeapp.brochure.presentation.brochure_list.BrochureListState
import com.bonial.challengeapp.brochure.presentation.brochure_list.BrochureViewModel
import com.bonial.challengeapp.brochure.presentation.model.BrochureUI
import com.bonial.challengeapp.core.presentation.util.ObserveAsEvents
import com.bonial.challengeapp.ui.theme.ChallengeAppTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChallengeAppTheme {
                val viewModel: BrochureViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                val context = LocalContext.current

                ObserveAsEvents(events = viewModel.events) { event ->
                    when (event) {
                        is BrochureListEvent.Error -> {
                            Toast.makeText(
                                context,
                                event.error.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BrochureListScreen(
                        modifier = Modifier.padding(innerPadding),
                        isPreview = false,
                        state = state,
                        onAction = { action -> viewModel.onAction(action) }
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
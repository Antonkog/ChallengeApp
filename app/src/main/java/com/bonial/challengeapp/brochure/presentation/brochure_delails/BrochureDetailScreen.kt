package com.bonial.challengeapp.brochure.presentation.brochure_delails

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bonial.challengeapp.R
import com.bonial.challengeapp.brochure.domain.Constants
import com.bonial.challengeapp.brochure.presentation.brochure_list.BrochureListState
import com.bonial.challengeapp.brochure.presentation.brochure_list.components.previewBrochure
import com.bonial.challengeapp.core.presentation.util.roundToTwoDecimals
import com.bonial.challengeapp.ui.theme.ChallengeAppTheme

@Composable
fun BrochureDetailScreen(
    state: BrochureListState,
    modifier: Modifier = Modifier
) {
    val contentColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }
    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (state.selectedBrochure != null) {
        val brochure = state.selectedBrochure
        Card(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentSize(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                AsyncImage(
                    model = brochure.imageUrl,
                    contentDescription = stringResource(R.string.cd_brochure_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.height(12.dp))

                brochure.publisherName?.let {
                    Text(text = it, style = MaterialTheme.typography.titleMedium)
                }

                Text(
                    text = stringResource(
                        R.string.distance_km,
                        brochure.distanceKm.roundToTwoDecimals()
                    ),
                    color = contentColor,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = if (brochure.isPremium) Constants.BROCHURE_PREMIUM_TYPE else Constants.BROCHURE_TYPE,
                    color = contentColor,
                    style = MaterialTheme.typography.bodySmall
                )

                if (brochure.isExpired) {
                    Text(
                        text = stringResource(R.string.expired),
                        color = Color.Red,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }

    }
}


@PreviewLightDark
@Composable
private fun CoinDetailScreenPreview() {
    ChallengeAppTheme {
        BrochureDetailScreen(
            state = BrochureListState(
                selectedBrochure = previewBrochure,
            ),
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background
            )
        )
    }
}
package com.bonial.challengeapp.brochure.presentation.brochure_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.bonial.challengeapp.R
import com.bonial.challengeapp.brochure.domain.Brochure
import com.bonial.challengeapp.brochure.presentation.model.BrochureUI
import com.bonial.challengeapp.brochure.presentation.model.toBrochureUi
import com.bonial.challengeapp.ui.theme.ChallengeAppTheme

@Composable
fun BrochureListItem(
    isPreview: Boolean,
    brochureUi: BrochureUI,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        if(!isPreview)
        brochureUi.imageUrl?.let {
            CoilImage(
                imageUrl = it,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        else
            Image(
                painter = painterResource(id = R.drawable.ic_placeholder),
                contentDescription = "Preview Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp))
            )


        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = brochureUi.publisherName ?: "",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@PreviewLightDark
@Composable
private fun BrochureListItemPreview() {
    ChallengeAppTheme {
        BrochureListItem(
           isPreview =  true,
            brochureUi = previewBrochure,
            onClick = { /*TODO*/ },
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background
            )
        )
    }
}
internal val previewBrochure = Brochure(
    imageUrl = null,
    id = "De-01",
    title = "ALDI",
    publisherName = "ALDI",
    isExpired = false,
    distanceKm = 4.0,
    isPremium = false
).toBrochureUi()

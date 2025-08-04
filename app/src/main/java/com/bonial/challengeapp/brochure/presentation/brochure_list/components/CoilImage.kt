package com.bonial.challengeapp.brochure.presentation.brochure_list.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bonial.challengeapp.R

@Composable
fun CoilImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    placeholderRes: Int = R.drawable.ic_placeholder,
    errorRes: Int = R.drawable.ic_error
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        placeholder = painterResource(id = placeholderRes),
        fallback = painterResource(id = placeholderRes),
        error = painterResource(id = errorRes),
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}

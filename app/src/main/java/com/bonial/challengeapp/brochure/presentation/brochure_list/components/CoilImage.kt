package com.bonial.challengeapp.brochure.presentation.brochure_list.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter

@Composable
fun CoilImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    placeholderRes: Int = android.R.drawable.ic_menu_camera, // fallback drawable
    errorRes: Int = android.R.drawable.stat_notify_error
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        placeholder = painterResource(id = placeholderRes),
        error = painterResource(id = errorRes),
        fallback = painterResource(id = placeholderRes),
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}

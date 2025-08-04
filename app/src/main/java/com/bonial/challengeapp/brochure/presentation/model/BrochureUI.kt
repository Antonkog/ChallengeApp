package com.bonial.challengeapp.brochure.presentation.model

import com.bonial.challengeapp.brochure.domain.Brochure

data class BrochureUI(
    val imageUrl: String?,
    val publisherName: String?
)

fun Brochure.toBrochureUi(): BrochureUI {
    return BrochureUI(
        imageUrl = imageUrl,
        publisherName = publisherName
    )
}
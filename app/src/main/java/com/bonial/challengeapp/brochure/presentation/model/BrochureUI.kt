package com.bonial.challengeapp.brochure.presentation.model

import com.bonial.challengeapp.brochure.domain.Brochure

data class BrochureUI(
    val imageUrl: String?,
    val publisherName: String?,
    val isExpired: Boolean,
    val distanceKm: Double,
    val isPremium: Boolean
)

fun Brochure.toBrochureUi(): BrochureUI {
    return BrochureUI(
        imageUrl = imageUrl,
        publisherName = publisherName,
        isExpired = isExpired,
        distanceKm = distanceKm,
        isPremium = isPremium
    )
}
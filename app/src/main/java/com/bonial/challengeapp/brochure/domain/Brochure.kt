package com.bonial.challengeapp.brochure.domain

import java.time.ZonedDateTime


data class Brochure(
    val id: String,
    val title: String?,
    val imageUrl: String?,
    val publisherName: String,
    val publishedUntil: ZonedDateTime,
    val distanceKm: Double,
    val type: BrochureContentType
)

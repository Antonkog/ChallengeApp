package com.bonial.challengeapp.brochure.domain


data class Brochure(
    val id: Long,
    val title: String,
    val imageUrl: String?,
    val publisherName: String,
    val isExpired: Boolean,
    val distanceKm: Double
)

package com.bonial.challengeapp.brochure.data.networking.dto

import kotlinx.serialization.Serializable

@Suppress("UNUSED")
@Serializable
data class BrochureContentDto(
    val id: String,
    val title: String? = null,
    val brochureImage: String? = null,
    val imageUrl: String? = null,
    val publisher: PublisherDto? = null,
    val publishedFrom: String? = null,
    val publishedUntil: String? = null,
    val distance: Double? = null
)
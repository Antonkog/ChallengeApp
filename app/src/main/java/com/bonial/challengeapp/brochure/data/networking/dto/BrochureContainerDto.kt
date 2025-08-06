package com.bonial.challengeapp.brochure.data.networking.dto

import com.bonial.challengeapp.brochure.data.networking.ContentListOrSingleSerializer
import kotlinx.serialization.Serializable

@Suppress("UNUSED")
@Serializable
data class BrochureContainerDto(
    @Serializable(with = ContentListOrSingleSerializer::class)
    val content: List<BrochureContentDto> = listOf(),
    val contentType: String?
)
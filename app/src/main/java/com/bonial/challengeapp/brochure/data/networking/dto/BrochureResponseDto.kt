package com.bonial.challengeapp.brochure.data.networking.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BrochureResponseDto(
    @SerialName("_embedded")
    val embedded: EmbeddedDto
)
package com.bonial.challengeapp.brochure.data.networking.dto

import kotlinx.serialization.Serializable

@Suppress("UNUSED")
@Serializable
data class EmbeddedDto(
    val contents: List<BrochureContainerDto> = listOf()
)
package com.bonial.challengeapp.brochure.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class PublisherDto(
    val id: String? = null,
    val name: String? = null,
    val type: String? = null
)
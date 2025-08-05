package com.bonial.challengeapp.brochure.data.mappers


import com.bonial.challengeapp.brochure.data.networking.dto.BrochureContainerDto
import com.bonial.challengeapp.brochure.data.networking.dto.BrochureResponseDto
import com.bonial.challengeapp.brochure.domain.Brochure
import com.bonial.challengeapp.brochure.domain.BrochureContentType
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun BrochureResponseDto.toBrochures(): List<Brochure> {
    return embedded.contents.flatMap { container ->
        container.toBrochures()
    }
}

fun BrochureContainerDto.toBrochures(): List<Brochure> {
    return content.map { contentDto ->

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        val parsedUntil = ZonedDateTime.parse(contentDto.publishedUntil, formatter)

        Brochure(
            imageUrl = when (contentType) {
                "superBannerCarousel" -> contentDto.imageUrl
                else -> contentDto.brochureImage
            },
            publisherName = contentDto.publisher?.name ?: "Unknown",
            publishedUntil = parsedUntil,
            distanceKm = contentDto.distance ?: 0.0,
            type = BrochureContentType.fromString(contentType),
            id = contentDto.id,
            title = contentDto.title
        )
    }
}
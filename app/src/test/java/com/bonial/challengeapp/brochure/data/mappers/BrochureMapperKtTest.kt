package com.bonial.challengeapp.brochure.data.mappers

import com.bonial.challengeapp.brochure.data.networking.dto.BrochureResponseDto
import com.bonial.challengeapp.brochure.domain.BrochureContentType
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import java.time.ZonedDateTime

class BrochureMapperKtTest {

    private lateinit var jsonText: String
    private lateinit var responseDto: BrochureResponseDto

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Before
    fun setUp() {
        jsonText = loadJsonFromResources("brochures.json")
        responseDto = json.decodeFromString(jsonText)
    }

    fun loadJsonFromResources(filename: String): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream(filename)
            ?: throw IllegalArgumentException("Could not find $filename in test/resources")

        return inputStream.bufferedReader().use { it.readText() }
    }


    @Test
    fun `dto toBrochures should return non-empty list`() {
        val brochures = responseDto.toBrochures()
        assertThat(brochures).isNotEmpty()
    }

    @Test
    fun `brochure fields should be correctly mapped`() {
        val brochures = responseDto.toBrochures()

        assertThat(brochures.any { !it.imageUrl.isNullOrEmpty() }).isTrue()
        assertThat(brochures.any { !it.publisherName.isNullOrEmpty() }).isTrue()
        assertThat(brochures.any { it.distanceKm > 0.0 }).isTrue()
    }

    @Test
    fun `contains BROCHURE_PREMIUM and BROCHURE, publishedUntil is after now`() {
        val brochures = responseDto.toBrochures()

        val premium = brochures.filter { it.type == BrochureContentType.BROCHURE_PREMIUM }
        val brochure = brochures.filter { it.type == BrochureContentType.BROCHURE }
        val valid = brochures.filterNot { it.publishedUntil.isAfter(ZonedDateTime.now()) }

        assertThat(premium).isNotEmpty()
        assertThat(brochure).isNotEmpty()
        assertThat(valid).isNotEmpty()
    }
}

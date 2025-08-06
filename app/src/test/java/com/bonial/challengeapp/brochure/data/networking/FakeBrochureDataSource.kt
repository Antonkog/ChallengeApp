package com.bonial.challengeapp.brochure.data.networking

import com.bonial.challengeapp.brochure.data.mappers.toBrochures
import com.bonial.challengeapp.brochure.data.networking.dto.BrochureResponseDto
import com.bonial.challengeapp.brochure.domain.Brochure
import com.bonial.challengeapp.brochure.domain.BrochureDataSource
import com.bonial.challengeapp.core.domain.util.NetworkError
import com.bonial.challengeapp.core.domain.util.Result
import kotlinx.serialization.json.Json

class FakeBrochureDataSource : BrochureDataSource {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val brochures: List<Brochure>

    init {
        val jsonText = loadJsonFromResources("brochures.json")
        val responseDto = json.decodeFromString<BrochureResponseDto>(jsonText)
        brochures = responseDto.toBrochures()
        println("FakeBrochureDataSource: Converted brochures: ${brochures.size}")
    }

    private fun loadJsonFromResources(filename: String): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream(filename)
            ?: throw IllegalArgumentException("Could not find $filename in test/resources")
        return inputStream.bufferedReader().use { it.readText() }
    }

    override suspend fun getBrochures(): Result<List<Brochure>, NetworkError> {
        return Result.Success(brochures)
    }
}
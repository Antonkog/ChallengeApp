package com.bonial.challengeapp.brochure.data.networking

import com.bonial.challengeapp.brochure.data.mappers.toBrochures
import com.bonial.challengeapp.brochure.data.networking.dto.BrochureResponseDto
import com.bonial.challengeapp.brochure.domain.Brochure
import com.bonial.challengeapp.brochure.domain.BrochureContentType
import com.bonial.challengeapp.brochure.domain.BrochureDataSource
import com.bonial.challengeapp.core.data.networking.constructUrl
import com.bonial.challengeapp.core.data.networking.safeCall
import com.bonial.challengeapp.core.domain.util.NetworkError
import com.bonial.challengeapp.core.domain.util.Result
import com.bonial.challengeapp.core.domain.util.map
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteBrochureDataSource(
    private val httpClient: HttpClient
) : BrochureDataSource {

    override suspend fun getBrochures(): Result<List<Brochure>, NetworkError> {

        return safeCall<BrochureResponseDto> {
            httpClient.get(
                urlString = constructUrl("/shelf.json")
            )
        }.map { response ->
            response.toBrochures().filter {
                it.type in setOf(
                    BrochureContentType.BROCHURE,
                    BrochureContentType.BROCHURE_PREMIUM
                )
            }
        }
    }
}
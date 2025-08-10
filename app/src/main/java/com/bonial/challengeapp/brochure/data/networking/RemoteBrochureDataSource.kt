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

/**
 * Implementation of [BrochureDataSource] that retrieves brochure data from a remote API.
 * Uses Ktor HTTP client to make network requests and handles the conversion of DTO objects to domain models.
 *
 * @property httpClient The HTTP client used to make network requests
 */
class RemoteBrochureDataSource(
    private val httpClient: HttpClient
) : BrochureDataSource {

    /**
     * Retrieves a list of brochures from the remote API.
     * Makes a GET request to the shelf.json endpoint, converts the response to domain models,
     * and filters the results to include only brochures of type BROCHURE or BROCHURE_PREMIUM.
     *
     * @return A [Result] containing either a filtered list of [Brochure] objects if successful,
     *         or a [NetworkError] if the operation failed
     */
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
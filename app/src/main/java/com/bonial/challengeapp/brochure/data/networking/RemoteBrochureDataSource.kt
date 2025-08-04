package com.bonial.challengeapp.brochure.data.networking

import android.util.Log
import com.bonial.challengeapp.brochure.domain.Brochure
import com.bonial.challengeapp.brochure.domain.BrochureDataSource
import com.bonial.challengeapp.core.data.networking.constructUrl
import com.bonial.challengeapp.core.data.networking.safeCall
import com.bonial.challengeapp.core.domain.util.NetworkError
import com.bonial.challengeapp.core.domain.util.Result
import com.bonial.challengeapp.core.domain.util.map
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import parseBrochuresFlat

class RemoteBrochureDataSource(
    private val httpClient: HttpClient
) : BrochureDataSource {

    override suspend fun getBrochures(): Result<List<Brochure>, NetworkError> {
        return safeCall<String> {
            httpClient
                .get(constructUrl("/shelf.json"))

        }.map { response ->
            parseBrochuresFlat(response) {
                Log.e("Parse", "Failed to parse shelf.json", it)
            }
        }
    }
}
package com.bonial.challengeapp.core.data.networking

import com.bonial.challengeapp.core.domain.util.NetworkError
import com.bonial.challengeapp.core.domain.util.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

/**
 * Converts an [HttpResponse] to a [Result] object based on the HTTP status code.
 * Handles different status codes and maps them to appropriate [NetworkError] types.
 *
 * @param T The expected type of data to be parsed from the response body
 * @param response The HTTP response to convert
 * @return A [Result] containing either the parsed response body of type [T] or a [NetworkError]
 */
suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, NetworkError> {
    return when(response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch(e: NoTransformationFoundException) {
                Result.Error(NetworkError.SERIALIZATION)
            }
        }
        408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
        429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
        else -> Result.Error(NetworkError.UNKNOWN)
    }
}
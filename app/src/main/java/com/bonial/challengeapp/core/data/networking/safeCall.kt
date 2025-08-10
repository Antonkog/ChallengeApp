package com.bonial.challengeapp.core.data.networking

import com.bonial.challengeapp.core.domain.util.NetworkError
import com.bonial.challengeapp.core.domain.util.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException

/**
 * Executes a network call safely, handling common exceptions and converting the response to a [Result].
 * This function catches network-related exceptions and converts them to appropriate [NetworkError] types.
 *
 * @param T The expected type of data to be returned in case of success
 * @param execute A lambda function that performs the actual network request and returns an [HttpResponse]
 * @return A [Result] containing either the parsed response data of type [T] or a [NetworkError]
 */
suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, NetworkError> {
    val response = try {
        execute()
    } catch(e: UnresolvedAddressException) {
        return Result.Error(NetworkError.NO_INTERNET)
    } catch(e: SerializationException) {
        return Result.Error(NetworkError.SERIALIZATION)
    } catch(e: Exception) {
        currentCoroutineContext().ensureActive()
        return Result.Error(NetworkError.UNKNOWN)
    }

    return responseToResult(response)
}
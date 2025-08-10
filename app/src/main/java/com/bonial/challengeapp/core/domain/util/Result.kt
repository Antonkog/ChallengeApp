package com.bonial.challengeapp.core.domain.util

typealias DomainError = Error

/**
 * A sealed interface representing the result of an operation that can either succeed with data of type [D]
 * or fail with an error of type [E].
 *
 * @param D The type of data returned in case of success
 * @param E The type of error returned in case of failure, must extend [Error]
 */
sealed interface Result<out D, out E: Error> {
    /**
     * Represents a successful operation with the resulting data.
     *
     * @param data The data returned by the successful operation
     */
    data class Success<out D>(val data: D): Result<D, Nothing>
    
    /**
     * Represents a failed operation with the error that caused the failure.
     *
     * @param error The error that caused the operation to fail
     */
    data class Error<out E: DomainError>(val error: E): Result<Nothing, E>
}

/**
 * Transforms the data of a successful [Result] using the provided mapping function.
 * If the result is an error, it remains unchanged.
 *
 * @param T The type of data in the original result
 * @param E The type of error that might be contained in the result
 * @param R The type of data after transformation
 * @param map The function to transform the data if the result is successful
 * @return A new [Result] with either the transformed data or the original error
 */
inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

/**
 * Converts a [Result] with any data type to a [Result] with [Unit] data type.
 * This is useful when you want to discard the data but keep the success/error state.
 *
 * @param T The type of data in the original result
 * @param E The type of error that might be contained in the result
 * @return A [Result] with [Unit] as data type if successful, or the original error
 */
@Suppress("UNUSED")
fun <T, E: Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

/**
 * Executes the provided action if the result is successful, then returns the original result.
 * This is useful for side effects like logging or updating UI state.
 *
 * @param T The type of data in the result
 * @param E The type of error that might be contained in the result
 * @param action The function to execute with the data if the result is successful
 * @return The original [Result] unchanged
 */
inline fun <T, E: Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}

/**
 * Executes the provided action if the result is an error, then returns the original result.
 * This is useful for side effects like logging or displaying error messages.
 *
 * @param T The type of data in the result
 * @param E The type of error that might be contained in the result
 * @param action The function to execute with the error if the result is an error
 * @return The original [Result] unchanged
 */
inline fun <T, E: Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> {
            action(error)
            this
        }
        is Result.Success -> this
    }
}

/**
 * A type alias for a [Result] with [Unit] as the data type.
 * Used for operations that don't return meaningful data but can still fail with an error.
 *
 * @param E The type of error that might be contained in the result
 */
typealias EmptyResult<E> = Result<Unit, E>
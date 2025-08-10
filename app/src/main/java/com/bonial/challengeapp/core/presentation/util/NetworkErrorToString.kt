package com.bonial.challengeapp.core.presentation.util

import android.content.Context
import com.bonial.challengeapp.R
import com.bonial.challengeapp.core.domain.util.NetworkError

/**
 * Converts a NetworkError enum to a localized error message string.
 *
 * @param context The Android context used to retrieve the string resource.
 * @return A localized string representation of the network error.
 */
fun NetworkError.toString(context: Context): String {
    val resId = when(this) {
        NetworkError.REQUEST_TIMEOUT -> R.string.error_request_timeout
        NetworkError.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        NetworkError.NO_INTERNET -> R.string.error_no_internet
        NetworkError.SERVER_ERROR -> R.string.error_unknown
        NetworkError.SERIALIZATION -> R.string.error_serialization
        NetworkError.UNKNOWN -> R.string.error_unknown
    }
    return context.getString(resId)
}
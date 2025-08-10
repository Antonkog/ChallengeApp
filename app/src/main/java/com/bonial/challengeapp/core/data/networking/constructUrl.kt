package com.bonial.challengeapp.core.data.networking

import com.bonial.challengeapp.BuildConfig

/**
 * Constructs a complete URL by ensuring the base URL is properly prepended.
 * If the URL already contains the base URL, it is returned unchanged.
 * If the URL starts with a slash, the slash is removed before prepending the base URL.
 *
 * @param url The URL string to process
 * @return A complete URL with the base URL properly prepended
 */
fun constructUrl(url: String): String {
    return when {
        url.contains(BuildConfig.BASE_URL) -> url
        url.startsWith("/") -> BuildConfig.BASE_URL + url.drop(1)
        else -> BuildConfig.BASE_URL + url
    }
}
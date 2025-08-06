package com.bonial.challengeapp.brochure.domain

enum class BrochureContentType {
    SUPER_BANNER_CAROUSEL,
    BROCHURE,
    BROCHURE_PREMIUM,
    UNKNOWN;

    companion object {
        fun fromString(value: String?): BrochureContentType = when (value) {
            Constants.SUPER_BANNER_CAROUSEL_TYPE -> SUPER_BANNER_CAROUSEL
            Constants.BROCHURE_TYPE -> BROCHURE
            Constants.BROCHURE_PREMIUM_TYPE -> BROCHURE_PREMIUM
            else -> UNKNOWN
        }
    }
}
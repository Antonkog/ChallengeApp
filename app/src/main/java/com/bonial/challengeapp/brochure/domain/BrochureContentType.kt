package com.bonial.challengeapp.brochure.domain

enum class BrochureContentType {
    SUPER_BANNER_CAROUSEL,
    BROCHURE,
    BROCHURE_PREMIUM,
    UNKNOWN;

    companion object {
        fun fromString(value: String?): BrochureContentType = when (value) {
            "superBannerCarousel" -> SUPER_BANNER_CAROUSEL
            "brochure" -> BROCHURE
            "brochurePremium" -> BROCHURE_PREMIUM
            else -> UNKNOWN
        }
    }
}
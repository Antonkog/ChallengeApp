package com.bonial.challengeapp.brochure.domain

enum class BrochureContentType {
    SUPER_BANNER_CAROUSEL,
    BROCHURE,
    BROCHURE_PREMIUM,
    UNKNOWN;

    companion object {
        fun fromString(value: String?): BrochureContentType = when (value) {
            Contants.SuperBannerCarouselType -> SUPER_BANNER_CAROUSEL
            Contants.BrochureType -> BROCHURE
            Contants.BrochurePremiumType -> BROCHURE_PREMIUM
            else -> UNKNOWN
        }
    }
}
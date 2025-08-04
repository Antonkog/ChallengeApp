package com.bonial.challengeapp.brochure.domain

import com.bonial.challengeapp.core.domain.util.NetworkError
import com.bonial.challengeapp.core.domain.util.Result

interface BrochureDataSource {
    suspend fun getBrochures(): Result<List<Brochure>, NetworkError>
}
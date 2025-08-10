package com.bonial.challengeapp.brochure.domain

import com.bonial.challengeapp.core.domain.util.NetworkError
import com.bonial.challengeapp.core.domain.util.Result

/**
 * Interface defining the data source for brochure-related operations.
 * Implementations of this interface handle the retrieval of brochure data
 * from various sources (e.g., network, or json asset when testing).
 */
interface BrochureDataSource {
    suspend fun getBrochures(): Result<List<Brochure>, NetworkError>
}
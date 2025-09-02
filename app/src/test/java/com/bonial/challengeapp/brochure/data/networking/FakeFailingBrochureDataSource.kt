package com.bonial.challengeapp.brochure.data.networking

import com.bonial.challengeapp.brochure.domain.Brochure
import com.bonial.challengeapp.brochure.domain.BrochureDataSource
import com.bonial.challengeapp.core.domain.util.NetworkError
import com.bonial.challengeapp.core.domain.util.Result

class FakeFailingBrochureDataSource : BrochureDataSource {
    override suspend fun getBrochures(): Result<List<Brochure>, NetworkError> {
        return Result.Error(NetworkError.SERVER_ERROR)
    }
}

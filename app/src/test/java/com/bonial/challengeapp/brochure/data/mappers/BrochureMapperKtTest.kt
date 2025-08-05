package com.bonial.challengeapp.brochure.data.mappers

import com.bonial.challengeapp.brochure.data.networking.FakeBrochureDataSource
import com.bonial.challengeapp.brochure.domain.Brochure
import com.bonial.challengeapp.brochure.domain.BrochureContentType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.ZonedDateTime
import com.bonial.challengeapp.core.domain.util.Result

class BrochureMapperKtTest {

    private lateinit var brochures: List<Brochure>

    @Before
    fun setUp() = runBlocking {
        val repo = FakeBrochureDataSource()
        val result = repo.getBrochures()
        brochures = when (result) {
            is Result.Success -> result.data
            is Result.Error -> throw AssertionError("Failed to load fake brochures: ${result.error}")
        }
    }


    @Test
    fun `dto toBrochures should return non-empty list`() {
        assertThat(brochures).isNotEmpty()
    }

    @Test
    fun `brochure fields should be correctly mapped`() {
        assertThat(brochures.any { !it.imageUrl.isNullOrEmpty() }).isTrue()
        assertThat(brochures.any { !it.publisherName.isNullOrEmpty() }).isTrue()
        assertThat(brochures.any { it.distanceKm > 0.0 }).isTrue()
    }

    @Test
    fun `contains BROCHURE_PREMIUM and BROCHURE, publishedUntil is after now`() {
        val premium = brochures.filter { it.type == BrochureContentType.BROCHURE_PREMIUM }
        val brochure = brochures.filter { it.type == BrochureContentType.BROCHURE }
        val valid = brochures.filterNot { it.publishedUntil.isAfter(ZonedDateTime.now()) }

        assertThat(premium).isNotEmpty()
        assertThat(brochure).isNotEmpty()
        assertThat(valid).isNotEmpty()
    }
}

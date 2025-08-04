package com.bonial.challengeapp.brochure.presentation.brochure_list

import com.bonial.challengeapp.core.domain.util.NetworkError

sealed interface BrochureListEvent {
    data class Error(val error: NetworkError) : BrochureListEvent
}
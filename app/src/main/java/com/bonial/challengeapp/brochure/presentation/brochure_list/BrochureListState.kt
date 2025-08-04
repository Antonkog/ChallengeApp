package com.bonial.challengeapp.brochure.presentation.brochure_list

import androidx.compose.runtime.Immutable
import com.bonial.challengeapp.brochure.presentation.model.BrochureUI

@Immutable
data class BrochureListState(
    val isLoading: Boolean = false,
    val brochures: List<BrochureUI> = emptyList(),
    val selectedBrochure: BrochureUI? = null
)
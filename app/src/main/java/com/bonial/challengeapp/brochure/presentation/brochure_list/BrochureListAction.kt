package com.bonial.challengeapp.brochure.presentation.brochure_list

import com.bonial.challengeapp.brochure.presentation.model.BrochureUI

sealed interface BrochureListAction {
    data class OnBrochureClick(val brochureUi: BrochureUI) : BrochureListAction
}
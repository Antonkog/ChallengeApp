package com.bonial.challengeapp.core.presentation.util

import java.math.RoundingMode
import java.math.BigDecimal

fun Double.roundToTwoDecimals(): Double {
    return BigDecimal(this).setScale(2, RoundingMode.HALF_UP).toDouble()
}

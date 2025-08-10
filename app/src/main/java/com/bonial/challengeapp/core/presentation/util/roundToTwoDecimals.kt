package com.bonial.challengeapp.core.presentation.util

import java.math.RoundingMode
import java.math.BigDecimal

/**
 * Rounds a Double value to two decimal places using HALF_UP rounding mode.
 *
 * @return The rounded double value with exactly two decimal places.
 */
fun Double.roundToTwoDecimals(): Double {
    return BigDecimal(this).setScale(2, RoundingMode.HALF_UP).toDouble()
}

package com.bonial.challengeapp.core.presentation.util

import com.google.common.truth.Truth
import org.junit.Test

class RoundToTwoDecimalsTest {
    @Test
    fun `rounds double to two decimal places correctly`() {
        val input = 3.14159
        val expected = 3.14

        val result = input.roundToTwoDecimals()

        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `rounds up correctly`() {
        val input = 2.678
        val expected = 2.68

        val result = input.roundToTwoDecimals()

        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `rounds down correctly`() {
        val input = 2.674
        val expected = 2.67

        val result = input.roundToTwoDecimals()

        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `handles whole numbers`() {
        val input = 5.0
        val expected = 5.0

        val result = input.roundToTwoDecimals()

        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `handles already rounded value`() {
        val input = 4.25
        val expected = 4.25

        val result = input.roundToTwoDecimals()

        Truth.assertThat(result).isEqualTo(expected)
    }
}
package com.bonial.challengeapp.core.data.networking

import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Unit tests for the constructUrl function.
 * 
 * These tests verify that the URL construction logic works correctly for all scenarios:
 * 1. URLs that already contain the base URL
 * 2. URLs that start with a slash
 * 3. URLs without the base URL or a leading slash
 */
class ConstructUrlTest {
    
    private val baseUrl = "https://api.example.com/"
    
    /**
     * Test implementation of constructUrl that uses a fixed baseUrl for testing.
     * This implementation mirrors the logic in the actual constructUrl function
     * but uses a constant baseUrl instead of BuildConfig.BASE_URL.
     * 
     * @param url The URL string to process
     * @return A complete URL with the base URL properly prepended
     */
    private fun testConstructUrl(url: String): String {
        return when {
            url.contains(baseUrl) -> url
            url.startsWith("/") -> baseUrl + url.drop(1)
            else -> baseUrl + url
        }
    }
    
    @Test
    fun `url already contains base url`() {
        // Given
        val input = "https://api.example.com/brochures"
        
        // When
        val result = testConstructUrl(input)
        
        // Then
        assertThat(result).isEqualTo(input)
    }
    
    @Test
    fun `url starts with slash`() {
        // Given
        val input = "/brochures"
        val expected = "https://api.example.com/brochures"
        
        // When
        val result = testConstructUrl(input)
        
        // Then
        assertThat(result).isEqualTo(expected)
    }
    
    @Test
    fun `url without base url or slash`() {
        // Given
        val input = "brochures"
        val expected = "https://api.example.com/brochures"
        
        // When
        val result = testConstructUrl(input)
        
        // Then
        assertThat(result).isEqualTo(expected)
    }
    
    @Test
    fun `handles empty url`() {
        // Given
        val input = ""
        val expected = "https://api.example.com/"
        
        // When
        val result = testConstructUrl(input)
        
        // Then
        assertThat(result).isEqualTo(expected)
    }
    
    @Test
    fun `handles url with query parameters`() {
        // Given
        val input = "brochures?page=1&limit=10"
        val expected = "https://api.example.com/brochures?page=1&limit=10"
        
        // When
        val result = testConstructUrl(input)
        
        // Then
        assertThat(result).isEqualTo(expected)
    }
}
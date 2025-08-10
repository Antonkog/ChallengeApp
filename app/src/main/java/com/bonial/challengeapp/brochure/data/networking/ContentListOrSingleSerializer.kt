package com.bonial.challengeapp.brochure.data.networking

import com.bonial.challengeapp.brochure.data.networking.dto.BrochureContentDto
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.jsonObject

/**
 * A custom JSON serializer that handles both single brochure objects and lists of brochures.
 * This serializer normalizes different JSON formats into a consistent List<BrochureContentDto> format.
 * 
 * The serializer handles three main cases:
 * 1. JSON null values - converted to empty lists
 * 2. JSON arrays - filtered to extract valid content objects with IDs
 * 3. JSON objects - converted to single-element lists if they have an ID
 *
 * This class is implemented as a Kotlin `object` (singleton) to ensure only one instance exists
 * throughout the application, optimizing memory usage and maintaining consistent behavior.
 */
object ContentListOrSingleSerializer :
    JsonTransformingSerializer<List<BrochureContentDto>>(ListSerializer(BrochureContentDto.serializer())) {

    /**
     * Transforms the incoming JSON element into a standardized format for deserialization.
     * This method handles different JSON structures and normalizes them into a JsonArray
     * that can be properly deserialized into a List<BrochureContentDto>.
     *
     * @param element The JSON element to transform before deserialization
     * @return A JsonArray containing the normalized content ready for deserialization
     */
    public override fun transformDeserialize(element: JsonElement): JsonElement {
        return when (element) {
            is JsonNull -> {
                JsonArray(emptyList())
            }

            is JsonArray -> {
                // Handle superBannerCarousel: check for id in nested "content" object
                val filtered = element.filter { jsonElement ->
                    jsonElement is JsonObject && jsonElement.jsonObject["content"]?.jsonObject?.get(
                        "id"
                    ) != null
                }
                JsonArray(filtered.map { it.jsonObject["content"]!! }) // Extract the nested "content" object
            }

            is JsonObject -> {
                // Handle brochure/brochurePremium: id is directly in the object
                if (element.jsonObject["id"] != null) {
                    JsonArray(listOf(element))
                } else {
                    JsonArray(emptyList())
                }
            }

            else -> {
                JsonArray(emptyList())
            }
        }
    }
}
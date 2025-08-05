package com.bonial.challengeapp.brochure.data.networking

import com.bonial.challengeapp.brochure.data.networking.dto.BrochureContentDto
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.jsonObject

object ContentListOrSingleSerializer :
    JsonTransformingSerializer<List<BrochureContentDto>>(ListSerializer(BrochureContentDto.serializer())) {

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
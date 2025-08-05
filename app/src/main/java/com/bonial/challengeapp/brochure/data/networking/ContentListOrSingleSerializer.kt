package com.bonial.challengeapp.brochure.data.networking

import android.util.Log
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

    val TAG = ContentListOrSingleSerializer::class.java.name

    override fun transformDeserialize(element: JsonElement): JsonElement {
        return when {
            element is JsonNull -> {
               Log.d(TAG,"Warning: content field is null")
                JsonArray(emptyList())
            }

            element is JsonArray -> {
                // Handle superBannerCarousel: check for id in nested "content" object
                val filtered = element.filter { jsonElement ->
                    jsonElement is JsonObject && jsonElement.jsonObject["content"]?.jsonObject?.get(
                        "id"
                    ) != null
                }
                if (filtered.size < element.size) {
                   Log.d(TAG,"Filtered out ${element.size - filtered.size} objects missing 'id': ${element.filterNot { it in filtered }}")
                }
                JsonArray(filtered.map { it.jsonObject["content"]!! }) // Extract the nested "content" object
            }

            element is JsonObject -> {
                // Handle brochure/brochurePremium: id is directly in the object
                if (element.jsonObject["id"] != null) {
                    JsonArray(listOf(element))
                } else {
                    Log.d(TAG, "Object missing 'id': $element")
                    JsonArray(emptyList())
                }
            }

            else -> {
               Log.d(TAG,"Unexpected content type: $element")
                JsonArray(emptyList())
            }
        }
    }
}
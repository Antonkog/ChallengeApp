package com.bonial.challengeapp.brochure.data.networking

import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long
import org.junit.Test

class ContentListOrSingleSerializerTest {

    private val serializer = ContentListOrSingleSerializer

    @Test
    fun `returns empty list on JsonNull`() {
        val result = serializer.transformDeserialize(JsonNull)
        assertThat(result.jsonArray).isEmpty()
    }

    @Test
    fun `returns filtered nested content from JsonArray`() {
        val json = JsonArray(
            listOf(
                buildJsonObject {
                    put("content", buildJsonObject {
                        put("id", JsonPrimitive(123))
                        put("title", JsonPrimitive("Valid"))
                    })
                },
                buildJsonObject {
                    put("content", buildJsonObject { }) // Missing ID
                }
            )
        )

        val result = serializer.transformDeserialize(json)
        assertThat(result.jsonArray).hasSize(1)
        assertThat(result.jsonArray[0].jsonObject["id"]?.jsonPrimitive?.long).isEqualTo(123)
    }

    @Test
    fun `returns single element array when JsonObject has id`() {
        val json = buildJsonObject {
            put("id", JsonPrimitive(456))
            put("title", JsonPrimitive("Single"))
        }

        val result = serializer.transformDeserialize(json)
        assertThat(result.jsonArray).hasSize(1)
        assertThat(result.jsonArray[0].jsonObject["id"]?.jsonPrimitive?.long).isEqualTo(456)
    }

    @Test
    fun `returns empty array when JsonObject missing id`() {
        val json = buildJsonObject {
            put("title", JsonPrimitive("No ID"))
        }

        val result = serializer.transformDeserialize(json)
        assertThat(result.jsonArray).isEmpty()
    }

    @Test
    fun `returns empty array on unexpected type`() {
        val result = serializer.transformDeserialize(JsonPrimitive("unexpected"))
        assertThat(result.jsonArray).isEmpty()
    }
}
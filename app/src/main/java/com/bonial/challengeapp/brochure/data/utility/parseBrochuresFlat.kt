import com.bonial.challengeapp.brochure.domain.Brochure
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

suspend fun parseBrochuresFlat(
    jsonText: String,
    onError: ((Throwable) -> Unit)? = null
): List<Brochure> {
    val now = ZonedDateTime.now()
    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")

    val json = Json { ignoreUnknownKeys = true }
    val rootElement = runCatching { json.parseToJsonElement(jsonText).jsonObject }
        .onFailure { onError?.invoke(it) }
        .getOrNull() ?: return emptyList()

    val contents = rootElement["_embedded"]
        ?.jsonObject
        ?.get("contents")
        ?.jsonArray ?: return emptyList()

    return buildList {
        contents.forEach { sectionElement ->
            val section = sectionElement.jsonObject
            val type = section["contentType"]?.jsonPrimitive?.contentOrNull
            if (type !in setOf("brochure", "brochurePremium")) return@forEach

            val contentField = section["content"] ?: return@forEach
            val entries = when (contentField) {
                is JsonObject -> listOf(contentField)
                is JsonArray -> contentField
                else -> emptyList()
            }

            entries.forEach { entryElement ->
                val entry = entryElement.jsonObject

                val id = entry["id"]?.jsonPrimitive?.longOrNull ?: return@forEach
                val publisherName = entry["publisher"]?.jsonObject
                    ?.get("name")?.jsonPrimitive?.contentOrNull.orEmpty()
                val title = entry["title"]?.jsonPrimitive?.contentOrNull.orEmpty()

                val imageUrl = entry["brochureImage"]?.jsonPrimitive?.contentOrNull
                    ?: entry["imageUrl"]?.jsonPrimitive?.contentOrNull

                val publishedUntilStr = entry["publishedUntil"]?.jsonPrimitive?.contentOrNull
                val isExpired = publishedUntilStr?.let {
                    runCatching {
                        ZonedDateTime.parse(it, dateFormat).isBefore(now)
                    }.getOrDefault(true)
                } ?: true

                val distanceKm = entry["distance"]?.jsonPrimitive?.doubleOrNull
                    ?: Double.POSITIVE_INFINITY

                add(
                    Brochure(
                        id = id,
                        title = title,
                        imageUrl = imageUrl,
                        publisherName = publisherName,
                        isExpired = isExpired,
                        distanceKm = distanceKm
                    )
                )
            }
        }
    }
}

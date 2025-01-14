package m.a.nobahar.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import m.a.nobahar.domain.model.PoemRecitation

@Serializable
data class PoemRecitationDto(
    @SerialName("audioArtist")
    val artistName: String,
    val id: Long,
    val mp3Url: String,
    val xmlText: String?,
)

internal fun PoemRecitationDto.toPoemRecitation() = PoemRecitation(
    artistName = artistName,
    id = id,
    mp3Url = mp3Url,
    syncUrl = xmlText
)
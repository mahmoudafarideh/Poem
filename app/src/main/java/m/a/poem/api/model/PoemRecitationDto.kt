package m.a.poem.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import m.a.poem.domain.model.PoemRecitation

@Serializable
data class PoemRecitationDto(
    @SerialName("audioArtist")
    val artistName: String,
    val id: Long,
    val mp3Url: String
)

internal fun PoemRecitationDto.toPoemRecitation() = PoemRecitation(
    artistName = artistName,
    id = id,
    mp3Url = mp3Url,
)
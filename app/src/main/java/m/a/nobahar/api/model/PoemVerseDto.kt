package m.a.nobahar.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import m.a.nobahar.domain.model.PoemVerse

@Serializable
data class PoemVerseDto(
    val text: String,
    val id: Long,
    @SerialName("coupletIndex")
    val couple: Int
)

internal fun PoemVerseDto.toPoemVerse() = PoemVerse(
    text = text.replace("\n", " "),
    id = id,
    couple = couple
)
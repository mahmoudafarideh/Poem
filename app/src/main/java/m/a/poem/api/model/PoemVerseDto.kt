package m.a.poem.api.model

import kotlinx.serialization.Serializable
import m.a.poem.domain.model.PoemVerse

@Serializable
data class PoemVerseDto(
    val text: String,
    val id: Long,
)

internal fun PoemVerseDto.toPoemVerse() = PoemVerse(
    text = text,
    id = id
)
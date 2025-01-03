package m.a.poem.api.model

import kotlinx.serialization.Serializable
import m.a.poem.domain.model.PoemInfo

@Serializable
data class PoemInfoDto(
    val verses: List<PoemVerseDto>,
    val recitations: List<PoemRecitationDto>,
    val next: PoemDto?,
    val previous: PoemDto?,
)

internal fun PoemInfoDto.toPoemInfo() = PoemInfo(
    verses = verses.map { it.toPoemVerse() },
    nextPoem = next?.toBookItem(),
    previousPoem = previous?.toBookItem(),
    recitations = recitations.map { it.toPoemRecitation() }
)
package m.a.nobahar.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import m.a.nobahar.domain.model.PoemInfo

@Serializable
data class PoemInfoDto(
    val verses: List<PoemVerseDto>,
    val recitations: List<PoemRecitationDto>,
    val next: PoemDto?,
    val title: String,
    val previous: PoemDto?,
    @SerialName("category")
    val source: SourceDto,
) {
    @Serializable
    data class SourceDto(
        val poet: PoetInfoDto,
        @SerialName("cat")
        val book: PoetBookDto
    )
}

internal fun PoemInfoDto.toPoemInfo() = PoemInfo(
    verses = verses.map { it.toPoemVerse() },
    nextPoem = next?.toBookItem(),
    previousPoem = previous?.toBookItem(),
    recitations = recitations.map { it.toPoemRecitation() },
    poet = source.poet.toPoet(),
    book = source.book.toPoetBook(),
    label = title
)
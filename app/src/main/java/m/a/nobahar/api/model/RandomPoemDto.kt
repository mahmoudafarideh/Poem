package m.a.nobahar.api.model

import kotlinx.serialization.Serializable
import m.a.nobahar.domain.model.RandomPoem

@Serializable
data class RandomPoemDto(
    val verses: List<PoemVerseDto>,
    val title: String,
    val fullTitle: String,
    val id: Long
)

internal fun RandomPoemDto.toRandomPoem(
    poetInfoDto: PoetInfoDto,
    poetBookDto: PoetBookDto
) = RandomPoem(
    verses = verses.map { it.toPoemVerse() },
    poet = poetInfoDto.toPoet(),
    book = poetBookDto.toPoetBook(),
    id = id
)
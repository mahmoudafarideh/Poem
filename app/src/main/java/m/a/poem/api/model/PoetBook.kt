package m.a.poem.api.model

import kotlinx.serialization.Serializable
import m.a.poem.domain.model.PoetBook

@Serializable
data class PoetBookDto(
    val id: Long,
    val title: String,
)

@Serializable
data class PoetBooksDto(
    val children: List<PoetBookDto>
)

internal fun PoetBookDto.toPoetBook() = PoetBook(
    id = id,
    label = title,
)
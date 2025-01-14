package m.a.nobahar.api.model

import kotlinx.serialization.Serializable
import m.a.nobahar.domain.model.BookItem
import m.a.nobahar.domain.model.PoetBook

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

internal fun PoetBookDto.toBookItem() = BookItem.Book(
    id = id,
    label = title
)
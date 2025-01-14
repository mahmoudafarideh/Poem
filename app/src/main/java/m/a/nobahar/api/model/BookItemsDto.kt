package m.a.nobahar.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import m.a.nobahar.domain.model.PoetBookInfo

@Serializable
data class BookItemsDto(
    @SerialName("children")
    val books: List<PoetBookDto>,
    @SerialName("poems")
    val poems: List<PoemDto>,
    val title: String,
    val id: Long,
)

@Serializable
data class BookDetailDto(
    @SerialName("cat")
    val items: BookItemsDto,
)

internal fun BookDetailDto.toPoetBookInfo() = PoetBookInfo(
    id = items.id,
    label = items.title,
    items = items.toBookItems()
)

internal fun BookItemsDto.toBookItems() = listOf(
    books.map { it.toBookItem() },
    poems.map { it.toBookItem() }
).flatten()
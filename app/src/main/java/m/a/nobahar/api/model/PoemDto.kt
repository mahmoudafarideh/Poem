package m.a.nobahar.api.model

import kotlinx.serialization.Serializable
import m.a.nobahar.domain.model.BookItem

@Serializable
data class PoemDto(
    val id: Long,
    val title: String,
    val excerpt: String,
)

internal fun PoemDto.toBookItem() = BookItem.Poem(
    id = id,
    label = title,
    excerpt = excerpt
)
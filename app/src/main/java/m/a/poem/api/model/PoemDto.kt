package m.a.poem.api.model

import kotlinx.serialization.Serializable
import m.a.poem.domain.model.BookItem

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
package m.a.nobahar.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import m.a.nobahar.domain.model.BookItem
import m.a.nobahar.domain.model.PoemSearchResult

@Serializable
data class SearchResultDto(
    val id: Long,
    val title: String,
    val fullTitle: String,
    val plainText: String,
    @SerialName("category")
    val source: SourceDto
) {
    @Serializable
    data class SourceDto(
        @SerialName("poet")
        val poetDto: PoetDto,
        @SerialName("cat")
        val bookDto: BookDto
    )

    @Serializable
    data class BookDto(
        val id: Long,
        val bookName: String? = null,
    )
}

internal fun SearchResultDto.toSearch(query: String) = PoemSearchResult(
    poem = toPoem(query),
    poet = source.poetDto.toPoet(),
    book = BookItem.Book(source.bookDto.bookName.orEmpty(), source.bookDto.id)
)

internal fun SearchResultDto.toPoem(query: String) = BookItem.Poem(
    id = id,
    label = title,
    excerpt = plainText.split("\n").let {
        val queries = query.split(" ")
        it.map { verse ->
            verse to queries.map {
                if (verse.contains(it)) 1 else 0
            }.sum()
        }.maxBy {
            it.second
        }.first
    }
)

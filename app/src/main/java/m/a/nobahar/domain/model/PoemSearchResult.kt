package m.a.nobahar.domain.model

data class PoemSearchResult(
    val poem: BookItem.Poem,
    val book: BookItem.Book,
    val poet: Poet
)
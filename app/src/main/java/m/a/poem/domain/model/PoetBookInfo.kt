package m.a.poem.domain.model

data class PoetBookInfo(
    val id: Long,
    val label: String,
    val items: List<BookItem>
)

package m.a.nobahar.domain.model

data class PoetBookInfo(
    val id: Long,
    val label: String,
    val items: List<BookItem>
)

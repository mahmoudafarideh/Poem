package m.a.nobahar.domain.model

data class PoemSearchFilter(
    val query: String,
    val page: Int,
    val limit: Int,
    val poetId: Long?,
    val bookId: Long?
)
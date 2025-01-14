package m.a.nobahar.domain.model

data class RandomPoem(
    val verses: List<PoemVerse>,
    val poet: Poet,
    val book: PoetBook,
    val id: Long,
)
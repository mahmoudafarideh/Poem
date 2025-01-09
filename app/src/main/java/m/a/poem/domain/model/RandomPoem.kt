package m.a.poem.domain.model

data class RandomPoem(
    val verses: List<PoemVerse>,
    val poet: Poet,
    val book: PoetBook,
    val id: Long,
)
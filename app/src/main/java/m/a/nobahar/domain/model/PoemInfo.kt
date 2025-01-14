package m.a.nobahar.domain.model

data class PoemInfo(
    val verses: List<PoemVerse>,
    val recitations: List<PoemRecitation>,
    val nextPoem: BookItem.Poem?,
    val previousPoem: BookItem.Poem?,
    val poet: Poet,
    val book: PoetBook,
    val label: String
)
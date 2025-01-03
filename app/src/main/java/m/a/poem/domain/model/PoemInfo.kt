package m.a.poem.domain.model

data class PoemInfo(
    val verses: List<PoemVerse>,
    val nextPoem: BookItem.Poem?,
    val previousPoem: BookItem.Poem?,
)
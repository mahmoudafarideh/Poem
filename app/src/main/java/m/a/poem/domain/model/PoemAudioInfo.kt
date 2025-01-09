package m.a.poem.domain.model

data class PoemAudioInfo(
    val recitation: PoemRecitation,
    val poemExcerpt: String,
    val poemId: Long
)
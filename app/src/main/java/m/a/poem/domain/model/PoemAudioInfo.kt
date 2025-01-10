package m.a.poem.domain.model

data class PoemAudioInfo(
    val recitation: PoemRecitation,
    val poet: Poet,
    val poemInfo: Poem,
) {
    data class Poem(
        val excerpt: String,
        val id: Long
    )
}
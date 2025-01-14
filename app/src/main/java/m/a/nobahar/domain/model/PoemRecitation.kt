package m.a.nobahar.domain.model

data class PoemRecitation(
    val artistName: String,
    val id: Long,
    val mp3Url: String,
    val syncUrl: String?
)
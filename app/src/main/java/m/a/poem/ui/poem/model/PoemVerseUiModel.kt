package m.a.poem.ui.poem.model

data class PoemVerseUiModel(
    val text: String,
    val id: Long,
    val position: VersePosition,
) {
    enum class VersePosition {
        Start,
        End
    }
}
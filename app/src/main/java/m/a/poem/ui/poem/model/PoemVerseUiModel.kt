package m.a.poem.ui.poem.model

import m.a.poem.domain.model.PoemVerse

data class PoemVerseUiModel(
    val text: String,
    val id: Long,
    val position: VersePosition,
    val isHighlighted: Boolean = false
) {
    enum class VersePosition {
        Start,
        End
    }
}

internal fun PoemVerse.toPoemVerseUiModel(index: Int) = PoemVerseUiModel(
    text = text,
    id = id,
    position = when {
        index % 2 == 1 -> PoemVerseUiModel.VersePosition.End
        else -> PoemVerseUiModel.VersePosition.Start
    }
)
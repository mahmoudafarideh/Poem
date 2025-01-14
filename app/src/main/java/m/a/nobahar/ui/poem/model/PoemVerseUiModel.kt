package m.a.nobahar.ui.poem.model

import m.a.nobahar.domain.model.PoemVerse
import m.a.nobahar.ui.poem.model.PoemVerseUiModel.VerseInfo

data class PoemVerseUiModel(
    val first: VerseInfo,
    val second: VerseInfo,
    val index: Int,
    val isSelected: Boolean = false
) {
    data class VerseInfo(
        val text: String,
        val id: Long,
        val isHighlighted: Boolean = false
    )
}

internal fun VerseInfo.toPoemVerse() = PoemVerse(text = text, id = id)

internal fun Pair<PoemVerse, PoemVerse>.toPoemVerseUiModel(index: Int) = PoemVerseUiModel(
    first = VerseInfo(this.first.text, this.first.id),
    second = VerseInfo(this.second.text, this.second.id),
    index = index
)
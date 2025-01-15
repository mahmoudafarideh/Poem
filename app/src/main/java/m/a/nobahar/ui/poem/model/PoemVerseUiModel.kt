package m.a.nobahar.ui.poem.model

import m.a.nobahar.domain.model.PoemVerse
import m.a.nobahar.ui.poem.model.PoemVerseUiModel.VerseInfo

sealed class PoemVerseUiModel {
    abstract val index: Int
    abstract val isSelected: Boolean
    abstract val first: VerseInfo
    open val second: VerseInfo? = null
    open val third: VerseInfo? = null
    abstract val previousVerses: Int
    abstract fun toggleSelection(isSelected: Boolean): PoemVerseUiModel
    abstract fun toggleHighlight(index: Int, shouldHighlight: Boolean): PoemVerseUiModel
    abstract fun getString(): String

    data class Single(
        override val first: VerseInfo,
        override val index: Int,
        override val previousVerses: Int,
        override val isSelected: Boolean = false,
    ) : PoemVerseUiModel() {
        override fun toggleSelection(isSelected: Boolean): PoemVerseUiModel {
            return copy(isSelected = isSelected)
        }

        override fun toggleHighlight(index: Int, shouldHighlight: Boolean): PoemVerseUiModel {
            return copy(
                first.copy(isHighlighted = shouldHighlight && index >= previousVerses)
            )
        }

        override fun getString(): String = first.text
    }

    data class Double(
        override val first: VerseInfo,
        override val second: VerseInfo,
        override val index: Int,
        override val previousVerses: Int,
        override val isSelected: Boolean = false
    ) : PoemVerseUiModel() {
        override fun toggleSelection(isSelected: Boolean): PoemVerseUiModel {
            return copy(isSelected = isSelected)
        }

        override fun toggleHighlight(index: Int, shouldHighlight: Boolean): PoemVerseUiModel {
            return copy(
                first.copy(isHighlighted = shouldHighlight && index >= previousVerses),
                second.copy(isHighlighted = shouldHighlight && index >= previousVerses + 1)
            )
        }

        override fun getString(): String = first.text + "\n" + second.text
    }

    data class Triple(
        override val first: VerseInfo,
        override val second: VerseInfo,
        override val third: VerseInfo,
        override val index: Int,
        override val previousVerses: Int,
        override val isSelected: Boolean = false
    ) : PoemVerseUiModel() {
        override fun toggleSelection(isSelected: Boolean): PoemVerseUiModel {
            return copy(isSelected = isSelected)
        }

        override fun toggleHighlight(index: Int, shouldHighlight: Boolean): PoemVerseUiModel {
            return copy(
                first.copy(isHighlighted = shouldHighlight && index >= previousVerses),
                second.copy(isHighlighted = shouldHighlight && index >= previousVerses + 1),
                third.copy(isHighlighted = shouldHighlight && index >= previousVerses + 2),
            )
        }

        override fun getString(): String = first.text + "\n" + second.text + "\n" + third.text
    }

    data class VerseInfo(
        val text: String,
        val id: Long,
        val couple: Int,
        val isHighlighted: Boolean = false
    )
}

internal fun VerseInfo.toPoemVerse() = PoemVerse(text = text, id = id, couple = couple)

internal fun List<PoemVerse>.toPoemVerseUiModel(index: Int, previousVerses: Int) =
    when (this.size) {
        1 -> PoemVerseUiModel.Single(
            first = VerseInfo(this[0].text, this[0].id, this[0].couple),
            index = index,
            previousVerses = previousVerses
        )

        2 -> PoemVerseUiModel.Double(
            first = VerseInfo(this[0].text, this[0].id, this[0].couple),
            second = VerseInfo(this[1].text, this[1].id, this[1].couple),
            index = index,
            previousVerses = previousVerses
        )

        3 -> PoemVerseUiModel.Triple(
            first = VerseInfo(this[0].text, this[0].id, this[0].couple),
            second = VerseInfo(this[1].text, this[1].id, this[1].couple),
            third = VerseInfo(this[2].text, this[2].id, this[2].couple),
            index = index,
            previousVerses = previousVerses
        )

        else -> null
    }
package m.a.nobahar.ui.book.model

import m.a.nobahar.domain.model.BookItem
import m.a.nobahar.domain.model.PoetBook

sealed class BookSubItemUiModel {
    abstract val label: String
    abstract val id: Long
    abstract val itemId: String
}

data class BookItemUiModel(
    override val label: String,
    override val id: Long
) : BookSubItemUiModel() {
    override val itemId: String = "SubBook$id"

    companion object {
        val fixture = BookItemUiModel("غزلیات", 24)
    }
}

data class PoemItemUiModel(
    override val label: String,
    val excerpt: String,
    override val id: Long
) : BookSubItemUiModel() {
    override val itemId: String = "SubPoem$id"
}

internal fun BookItem.Poem.toPoemItemUiModel() = PoemItemUiModel(
    label = label, excerpt = excerpt, id = id
)

internal fun PoetBook.toBookItemUiModel() = BookItemUiModel(
    label = label, id = id
)
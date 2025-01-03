package m.a.poem.ui.book.model

sealed class BookSubItemUiModel {
    abstract val label: String
    abstract val id: Long
    abstract val itemId: String
}

data class SubBook(
    override val label: String,
    override val id: Long
) : BookSubItemUiModel() {
    override val itemId: String = "SubBook$id"
}

data class SubPoem(
    override val label: String,
    val excerpt: String,
    override val id: Long
) : BookSubItemUiModel() {

    override val itemId: String = "SubPoem$id"
}
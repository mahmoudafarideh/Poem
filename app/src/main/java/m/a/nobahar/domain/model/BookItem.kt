package m.a.nobahar.domain.model

sealed class BookItem {
    abstract val id: Long
    abstract val label: String

    data class Poem(
        override val label: String,
        val excerpt: String,
        override val id: Long
    ) : BookItem()

    data class Book(
        override val label: String,
        override val id: Long
    ) : BookItem()
}
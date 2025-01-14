package m.a.nobahar.analytics

data class PoetScreenEvent(
    val id: Long,
    val nickName: String
) : AnalyticsEvent {
    override val key: String = "PoetScreen"
    override val parameters: Map<String, Any?> = mapOf(
        "id" to id, "name" to nickName,
    )
}

data class PoetBookScreenEvent(
    val poetId: Long,
    val poetBookId: Long,
    val nickName: String,
    val bookName: String,
) : AnalyticsEvent {
    override val key: String = "PoetBookScreen"
    override val parameters: Map<String, Any?> = mapOf(
        "id" to poetId,
        "name" to nickName,
        "bookId" to poetBookId,
        "bookName" to bookName,
    )
}

data class SearchScreenEvent(
    val poetId: Long?,
    val poetBookId: Long?,
    val nickName: String?,
    val bookName: String?,
) : AnalyticsEvent {
    override val key: String = "SearchScreen"
    override val parameters: Map<String, Any?> = mapOf(
        "id" to poetId,
        "name" to nickName,
        "bookId" to poetBookId,
        "bookName" to bookName,
    )
}

data class PoemScreenEvent(
    val poemId: Long,
) : AnalyticsEvent {
    override val key: String = "PoemScreen"
    override val parameters: Map<String, Any?> = mapOf(
        "id" to poemId,
    )
}

data class PoemFromWidgetScreenEvent(
    val poemId: Long,
) : AnalyticsEvent {
    override val key: String = "PoemFromWidgetScreen"
    override val parameters: Map<String, Any?> = mapOf(
        "id" to poemId,
    )
}

data object OmenScreenEvent : AnalyticsEvent {
    override val key: String = "OmenScreen"
    override val parameters: Map<String, Any?> = mapOf()
}

data object AppInfoEvent : AnalyticsEvent {
    override val key: String = "AppInfoDialog"
    override val parameters: Map<String, Any?> = mapOf()
}
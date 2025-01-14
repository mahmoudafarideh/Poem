package m.a.nobahar.analytics

interface AnalyticsEvent {
    val key: String
    val parameters: Map<String, Any?>
}
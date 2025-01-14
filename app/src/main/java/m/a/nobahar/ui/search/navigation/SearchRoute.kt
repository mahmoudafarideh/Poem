package m.a.nobahar.ui.search.navigation

import m.a.compilot.common.RouteNavigation
import m.a.nobahar.analytics.AppMetricaAgent
import m.a.nobahar.analytics.SearchScreenEvent
import m.a.nobahar.domain.model.Poet

@RouteNavigation
data class SearchRoute(
    val poetInfo: Poet?,
    val book: Book?,
) {
    init {
        AppMetricaAgent.log(
            SearchScreenEvent(poetInfo?.id, book?.id, poetInfo?.nickName, book?.name)
        )
    }
    data class Book(
        val id: Long,
        val name: String,
    )

    companion object
}
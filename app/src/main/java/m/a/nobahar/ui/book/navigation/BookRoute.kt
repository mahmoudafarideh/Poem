package m.a.nobahar.ui.book.navigation

import m.a.compilot.common.RouteNavigation
import m.a.nobahar.analytics.AppMetricaAgent
import m.a.nobahar.analytics.PoetBookScreenEvent
import m.a.nobahar.domain.model.Poet

@RouteNavigation
data class BookRoute(
    val poetInfo: Poet,
    val bookId: Long,
    val bookName: String,
) {
    init {
        AppMetricaAgent.log(PoetBookScreenEvent(poetInfo.id, bookId, poetInfo.nickName, bookName))
    }

    companion object
}
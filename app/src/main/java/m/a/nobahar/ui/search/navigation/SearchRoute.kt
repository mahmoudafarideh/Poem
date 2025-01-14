package m.a.nobahar.ui.search.navigation

import m.a.compilot.common.RouteNavigation
import m.a.nobahar.domain.model.Poet

@RouteNavigation
data class SearchRoute(
    val poetInfo: Poet?,
    val book: Book?,
) {
    data class Book(
        val id: Long,
        val name: String,
    )

    companion object
}
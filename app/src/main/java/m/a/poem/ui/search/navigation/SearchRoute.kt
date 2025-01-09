package m.a.poem.ui.search.navigation

import m.a.compilot.common.RouteNavigation
import m.a.poem.domain.model.Poet

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
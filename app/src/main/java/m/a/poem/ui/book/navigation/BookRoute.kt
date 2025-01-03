package m.a.poem.ui.book.navigation

import m.a.compilot.common.RouteNavigation
import m.a.poem.domain.model.Poet

@RouteNavigation
data class BookRoute(
    val poetInfo: Poet,
    val bookId: Long
) {
    companion object
}
package m.a.nobahar.ui.book.navigation

import m.a.compilot.common.RouteNavigation
import m.a.nobahar.domain.model.Poet

@RouteNavigation
data class BookRoute(
    val poetInfo: Poet,
    val bookId: Long,
    val bookName: String,
) {
    companion object
}
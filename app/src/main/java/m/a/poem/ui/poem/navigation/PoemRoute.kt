package m.a.poem.ui.poem.navigation

import m.a.compilot.common.RouteNavigation
import m.a.poem.domain.model.Poet

@RouteNavigation
data class PoemRoute(
    val poetInfo: Poet,
    val poemId: Long,
) {
    companion object
}
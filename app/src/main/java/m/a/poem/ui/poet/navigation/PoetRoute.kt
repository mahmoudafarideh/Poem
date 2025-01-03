package m.a.poem.ui.poet.navigation

import m.a.compilot.common.RouteNavigation
import m.a.poem.domain.model.Poet

@RouteNavigation
data class PoetRoute(
    val poetInfo: Poet
) {
    companion object
}
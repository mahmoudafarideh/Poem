package m.a.nobahar.ui.poet.navigation

import m.a.compilot.common.RouteNavigation
import m.a.nobahar.domain.model.Poet

@RouteNavigation
data class PoetRoute(
    val poetInfo: Poet
) {
    companion object
}
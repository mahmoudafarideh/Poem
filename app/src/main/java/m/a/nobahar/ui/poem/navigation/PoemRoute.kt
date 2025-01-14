package m.a.nobahar.ui.poem.navigation

import m.a.compilot.common.RouteNavigation
import m.a.nobahar.analytics.AppMetricaAgent
import m.a.nobahar.analytics.PoemScreenEvent

@RouteNavigation
data class PoemRoute(val poemId: Long) {
    init {
        AppMetricaAgent.log(PoemScreenEvent(poemId))
    }

    companion object
}
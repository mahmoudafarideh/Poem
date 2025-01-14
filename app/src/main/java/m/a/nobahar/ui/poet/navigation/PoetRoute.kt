package m.a.nobahar.ui.poet.navigation

import m.a.compilot.common.RouteNavigation
import m.a.nobahar.analytics.AppMetricaAgent
import m.a.nobahar.analytics.PoetScreenEvent
import m.a.nobahar.domain.model.Poet

@RouteNavigation
data class PoetRoute(
    val poetInfo: Poet
) {
    init {
        AppMetricaAgent.log(PoetScreenEvent(poetInfo.id, poetInfo.nickName))
    }
    companion object
}
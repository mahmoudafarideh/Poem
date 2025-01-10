package m.a.poem.ui.poem.navigation

import m.a.compilot.common.RouteNavigation

@RouteNavigation
data class PoemRoute(val poemId: Long) {
    companion object
}
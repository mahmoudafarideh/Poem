package m.a.poem.ui.poet.navigation

import androidx.navigation.NavGraphBuilder
import m.a.poem.ui.poet.navigation.routes.screen

fun NavGraphBuilder.poetGraph() {
    PoetRoute.screen(this) {

    }
}
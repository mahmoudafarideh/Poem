package m.a.poem.ui.artwork.navigation

import androidx.navigation.NavGraphBuilder
import m.a.poem.ui.artwork.navigation.routes.screen
import m.a.poem.ui.artwork.screen.ArtworkScreen

fun NavGraphBuilder.artworkGraph() {
    ArtworkRoute.screen(this) {
        ArtworkScreen(
            it.argument.first.text,
            it.argument.second.text,
            it.argument.poetName,
            it.argument.poemBook
        )
    }
}
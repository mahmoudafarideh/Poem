package m.a.nobahar.ui.info.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import m.a.nobahar.ui.info.navigation.routes.dialog
import m.a.nobahar.ui.info.screen.InfoDialog

fun NavGraphBuilder.infoGraph() {
    InfoRoute.dialog(this) {
        InfoDialog(
            modifier = Modifier,
        )
    }
}
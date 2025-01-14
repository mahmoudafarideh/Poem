package m.a.nobahar.ui.main.component

import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import m.a.compilot.navigation.LocalNavController
import m.a.compilot.navigation.comPilotNavController
import m.a.nobahar.ui.main.PoemPlayerViewModel
import m.a.nobahar.ui.main.model.MediaPlayerUiModel
import m.a.nobahar.ui.poem.navigation.PoemRoute
import m.a.nobahar.ui.poem.navigation.routes.navigator


@Composable
internal fun PoemPlayerContent(
    activity: Activity,
    modifier: Modifier = Modifier
) {
    val poemPlayerViewModel: PoemPlayerViewModel = hiltViewModel()
    val state = poemPlayerViewModel.state.collectAsState().value
    val navigation = LocalNavController.comPilotNavController
    PoemPlayerBar(
        state = state,
        onCloseClick = {
            poemPlayerViewModel.closeClicked()
        },
        onPlayClick = {
            poemPlayerViewModel.playClicked()
        },
        onPauseClick = {
            poemPlayerViewModel.pauseClicked()
        },
        modifier = modifier.clickable {
            poemPlayerViewModel.poemAudio?.let {
                val route = PoemRoute(it.poemInfo.id).navigator
                navigation.checkNotInRoutes(route.route()).navigate(route)
            }
        }
    )
    LaunchedEffect(state) {
        when (state?.state) {
            MediaPlayerUiModel.State.Playing -> {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }

            else -> {
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }
        }
    }
}
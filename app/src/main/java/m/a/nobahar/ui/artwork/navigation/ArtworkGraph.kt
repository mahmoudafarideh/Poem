package m.a.nobahar.ui.artwork.navigation

import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.launch
import m.a.nobahar.ui.artwork.navigation.routes.screen
import m.a.nobahar.ui.artwork.screen.ArtworkScreen
import m.a.nobahar.ui.artwork.screen.ArtworkViewModel
import m.a.nobahar.ui.goToMatnnegarMarket

@OptIn(ExperimentalComposeApi::class)
fun NavGraphBuilder.artworkGraph() {
    ArtworkRoute.screen(this) {
        val viewModel: ArtworkViewModel = hiltViewModel()
        val state = viewModel.state.collectAsStateWithLifecycle().value
        val captureController = rememberCaptureController()
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current
        ArtworkScreen(
            firstVerse = it.argument.first.text,
            secondVerse = it.argument.second.text,
            poetName = it.argument.poetName,
            state = state,
            onTabClick = {
                viewModel.tabClicked(it)
            },
            onFontClick = {
                viewModel.fontClicked(it)
            },
            onFontSizeChange = {
                viewModel.fontSizeChanged(it)
            },
            onColorClick = {
                viewModel.colorClicked(it)
            },
            onSaveButtonClick = {
                viewModel.saveButtonClicked()
                coroutineScope.launch {
                    val bitmapAsync = captureController.captureAsync()
                    try {
                        viewModel.bitmapLoaded(bitmapAsync.await().asAndroidBitmap())
                    } catch (_: Throwable) {
                        viewModel.savingFailed()
                    }
                }
            },
            onBackgroundClick = {
                viewModel.backgroundClicked(it)
            },
            captureController = captureController,
            onMatnnegarClick = {
                context.goToMatnnegarMarket()
            }
        )
    }
}
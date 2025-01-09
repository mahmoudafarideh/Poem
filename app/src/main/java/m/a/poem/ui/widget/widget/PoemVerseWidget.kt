package m.a.poem.ui.widget.widget

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import m.a.poem.ui.main.MainActivity
import m.a.poem.ui.main.MainActivity.Companion.KEY_DESTINATION
import m.a.poem.ui.poem.navigation.PoemRoute
import m.a.poem.ui.poem.navigation.routes.navigator
import m.a.poem.ui.widget.components.WidgetContent
import m.a.poem.ui.widget.viewmodel.PoemWidgetViewModel
import javax.inject.Inject
import javax.inject.Singleton

private val destinationKey = ActionParameters.Key<String>(
    KEY_DESTINATION
)

@Singleton
class PoemVerseWidget @Inject constructor(
    private val viewModel: PoemWidgetViewModel
) : GlanceAppWidget() {

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        provideContent {
            val state = viewModel.state.collectAsState().value

            WidgetContent(
                state = state,
                onRetryClick = { viewModel.retryClicked() },
                onRefreshClick = { viewModel.refreshClicked() },
                onPoemClick = viewModel.currentPoem?.let {
                    val parameters =
                        actionParametersOf(destinationKey to PoemRoute(it.poet, it.id).navigator())
                    actionStartActivity<MainActivity>(parameters)
                }
            )
        }
    }
}
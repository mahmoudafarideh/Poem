package m.a.poem.ui.widget.widget

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.glance.GlanceId
import androidx.glance.LocalContext
import androidx.glance.action.actionParametersOf
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionStartService
import androidx.glance.appwidget.provideContent
import m.a.poem.ui.MainActivity
import m.a.poem.ui.widget.components.WidgetContent
import m.a.poem.ui.widget.viewmodel.PoemWidgetViewModel
import javax.inject.Inject
import javax.inject.Singleton

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
                onRetryClick = {
                    viewModel.retryClicked()
                },
                onRefreshClick = {
                    viewModel.refreshClicked()
                },
                onPoemClick = viewModel.currentPoem?.let {
                    actionStartActivity<MainActivity>(
                        parameters = actionParametersOf()
                    )
                }
            )
        }
    }
}
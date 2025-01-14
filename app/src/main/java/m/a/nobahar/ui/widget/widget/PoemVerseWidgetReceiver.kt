package m.a.nobahar.ui.widget.widget

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PoemVerseWidgetReceiver : GlanceAppWidgetReceiver() {

    @Inject
    lateinit var poemVerseWidget: PoemVerseWidget
    override val glanceAppWidget: GlanceAppWidget by lazy {
        poemVerseWidget
    }

}
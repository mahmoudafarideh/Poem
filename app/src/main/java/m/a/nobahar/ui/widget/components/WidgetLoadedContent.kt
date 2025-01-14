package m.a.nobahar.ui.widget.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.height
import m.a.nobahar.R

@Composable
internal fun WidgetLoadedContent(
    firstVerse: String,
    secondVerse: String,
    poet: String,
    book: String,
    modifier: GlanceModifier = GlanceModifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlanceText(
            text = firstVerse,
            font = R.font.vazir_medium,
            fontSize = 18.sp,
            modifier = GlanceModifier
        )
        Spacer(
            modifier = GlanceModifier.height(8.dp)
        )
        GlanceText(
            text = secondVerse,
            font = R.font.vazir_medium,
            fontSize = 18.sp,
            modifier = GlanceModifier
        )
        Spacer(
            modifier = GlanceModifier.height(16.dp)
        )
        GlanceText(
            text = "$poet » $book",
            font = R.font.vazir_light,
            fontSize = 16.sp,
            modifier = GlanceModifier
        )
    }
}
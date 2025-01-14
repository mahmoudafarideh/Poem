package m.a.nobahar.ui.widget.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.height
import androidx.glance.layout.width

@Composable
internal fun WidgetLoadingContent(
    modifier: GlanceModifier = GlanceModifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = GlanceModifier
                .cornerRadius(12.dp)
                .height(24.dp)
                .width(224.dp)
                .background(Color.Gray.copy(alpha = .7f))
        )
        Spacer(
            modifier = GlanceModifier.height(16.dp)
        )
        Spacer(
            modifier = GlanceModifier
                .cornerRadius(12.dp)
                .height(24.dp)
                .width(232.dp)
                .background(Color.Gray.copy(alpha = .7f))
        )
        Spacer(
            modifier = GlanceModifier.height(16.dp)
        )
        Spacer(
            modifier = GlanceModifier
                .cornerRadius(12.dp)
                .height(22.dp)
                .width(86.dp)
                .background(Color.Gray.copy(alpha = .7f))
        )
    }
}
package m.a.poem.ui.widget.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.cornerRadius
import androidx.glance.layout.Alignment
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.size
import m.a.poem.R

@Composable
internal fun WidgetTitleBar(
    onRefreshClick: () -> Unit,
    modifier: GlanceModifier = GlanceModifier
) {

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Row(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {

            Image(
                provider = ImageProvider(android.R.drawable.ic_menu_rotate),
                contentDescription = null,
                modifier = GlanceModifier
                    .size(36.dp)
                    .cornerRadius(16.dp)
                    .clickable(onRefreshClick)
            )
            Spacer(modifier = GlanceModifier.size(12.dp))
            GlanceText(
                text = "تک‌بیت روز | صبا",
                font = R.font.vazir_medium,
                fontSize = 14.sp
            )
            Spacer(modifier = GlanceModifier.size(12.dp))
            Image(
                provider = ImageProvider(R.mipmap.saba_logo),
                contentDescription = null,
                modifier = GlanceModifier.size(36.dp)
            )
        }
    }
}
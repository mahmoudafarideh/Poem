package m.a.nobahar.ui.widget.components

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.cornerRadius
import androidx.glance.layout.Alignment
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.unit.ColorProvider
import m.a.nobahar.R

@SuppressLint("RestrictedApi")
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
                provider = ImageProvider(R.drawable.ic_rotate_right),
                contentDescription = null,
                modifier = GlanceModifier
                    .size(32.dp)
                    .cornerRadius(16.dp)
                    .clickable(onRefreshClick)
                    .padding(4.dp),
                colorFilter = ColorFilter.tint(ColorProvider(Color.Black))
            )
            Spacer(modifier = GlanceModifier.size(12.dp))
            GlanceText(
                text = "نوبهار | تک‌بیت روز",
                font = R.font.vazir_medium,
                fontSize = 14.sp
            )
            Spacer(modifier = GlanceModifier.size(12.dp))
            Image(
                provider = ImageProvider(R.mipmap.round_logo),
                contentDescription = null,
                modifier = GlanceModifier.size(36.dp)
            )
        }
    }
}
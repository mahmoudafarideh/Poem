package m.a.nobahar.ui.widget.components

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.unit.ColorProvider
import m.a.nobahar.R
import m.a.nobahar.ui.theme.WidgetPoemTheme


@SuppressLint("RestrictedApi")
@Composable
fun WidgetFetchingDataFailed(
    onRetryClick: () -> Unit,
    modifier: GlanceModifier = GlanceModifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = GlanceModifier
                .cornerRadius(26.dp)
                .background(MaterialTheme.colorScheme.errorContainer)
        ) {
            Image(
                provider = ImageProvider(android.R.drawable.stat_sys_warning),
                contentDescription = null,
                modifier = GlanceModifier
                    .size(56.dp)
                    .padding(8.dp),
                colorFilter = ColorFilter.tint(ColorProvider(MaterialTheme.colorScheme.error))
            )
        }
        Spacer(modifier = GlanceModifier.size(24.dp))
        GlanceText(
            text = "مشکلی در اتصال به اینترنت پیش آمد!",
            font = R.font.vazir_medium,
            fontSize = 14.sp,
        )
        Spacer(modifier = GlanceModifier.size(24.dp))
        GlanceText(
            text = "تلاش دوباره",
            font = R.font.vazir_medium,
            fontSize = 14.sp,
            modifier = GlanceModifier
                .padding(vertical = 8.dp, horizontal = 24.dp)
                .cornerRadius(24.dp)
                .clickable { onRetryClick() }
                .background(MaterialTheme.colorScheme.primary),
            color = Color.White
        )
    }
}


@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = 420, heightDp = 220)
@Composable
fun WidgetFetchingDataFailedPreview() {
    WidgetPoemTheme {
        WidgetFetchingDataFailed(
            onRetryClick = {},
            modifier = GlanceModifier.fillMaxWidth()
        )
    }
}
package m.a.poem.ui.shared.ui

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Devices.NEXUS_7
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(
    device = NEXUS_7,
    name = "NEXUS_7-LIGHT"
)
@Preview(
    device = NEXUS_7,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "NEXUS_7-DARK"
)
@Preview(
    device = Devices.PIXEL_TABLET,
    name = "PIXEL-TABLET-LIGHT"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_TABLET,
    name = "PIXEL-TABLET-LIGHT"
)
@Preview(
    device = "spec:width=1280dp,height=400dp,dpi=240",
    name = "TABLET-LIGHT"
)
@Preview(
    device = "spec:width=1280dp,height=400dp,dpi=240",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "TABLET-DARK",
)
annotation class SabaPreview()
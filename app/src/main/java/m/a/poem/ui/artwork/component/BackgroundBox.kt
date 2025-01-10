package m.a.poem.ui.artwork.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import m.a.poem.R
import m.a.poem.ui.shared.ui.SabaPreview
import m.a.poem.ui.theme.PoemThemePreview

@Composable
fun BackgroundBox(
    @DrawableRes
    image: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(86.dp)
            .clip(RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@SabaPreview
@Composable
private fun BackgroundBoxPreview() {
    PoemThemePreview {
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            BackgroundBox(
                image = R.drawable.bg_poem_1,
            )
            BackgroundBox(
                image = R.drawable.bg_poem_1,
            )
        }
    }
}
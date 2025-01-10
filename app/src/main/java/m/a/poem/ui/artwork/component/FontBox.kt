package m.a.poem.ui.artwork.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import m.a.poem.ui.shared.ui.SabaPreview
import m.a.poem.ui.theme.PoemThemePreview

@Composable
fun FontBox(
    fontName: String,
    fontFamily: FontFamily,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .width(86.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                MaterialTheme.colorScheme.secondaryContainer
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = fontName,
            fontFamily = fontFamily,
            fontSize = 16.sp,
            modifier = Modifier,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@SabaPreview
@Composable
private fun FontBoxPreview() {
    PoemThemePreview {
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            FontBox(
                fontName = "اردیبهشت",
                fontFamily = ordibeheshtFont,
            )
            FontBox(
                fontName = "نستعلیق",
                fontFamily = nastaliqFont,
            )
            FontBox(
                fontName = "وزیر",
                fontFamily = vazirFont,
            )
        }
    }
}
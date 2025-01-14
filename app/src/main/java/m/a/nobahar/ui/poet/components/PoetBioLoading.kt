package m.a.nobahar.ui.poet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import m.a.nobahar.ui.shared.ui.LocalWindowSize
import m.a.nobahar.ui.shared.ui.SabaPreview
import m.a.nobahar.ui.theme.PoemThemePreview

@Composable
internal fun PoetBioLoading(
    modifier: Modifier = Modifier
) {
    val windowSize = LocalWindowSize.current
    val horizontalPadding = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> 48.dp
        else -> 0.dp
    }
    Column(modifier = modifier) {
        repeat(3) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding)
                    .padding(vertical = 18.dp)
                    .padding(horizontal = 24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Book,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.size(12.dp))
                Spacer(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .size(48.dp, 12.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
            }
            HorizontalDivider(
                modifier = Modifier
                    .padding(horizontal = horizontalPadding)
                    .padding(start = 56.dp)
            )
        }
    }
}

@Composable
@SabaPreview
internal fun PoetBioLoadingPreview() {
    PoemThemePreview {
        PoetBioLoading()
    }
}
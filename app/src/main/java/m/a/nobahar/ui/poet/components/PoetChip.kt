package m.a.nobahar.ui.poet.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import m.a.nobahar.ui.theme.PoemThemePreview

@Composable
fun PoetChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall
            )
        },
        shape = CircleShape,
        modifier = modifier
    )
}

@Preview
@Composable
private fun PoetChipPreview() {
    PoemThemePreview {
        PoetChip(
            label = "زندگی‌نامه",
            onClick = {},
            isSelected = false
        )
    }
}
package m.a.nobahar.ui.home.component

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import m.a.nobahar.ui.home.model.CenturyUiModel
import m.a.nobahar.ui.theme.PoemThemePreview

@Composable
fun CenturyChip(
    century: CenturyUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = century.isSelected,
        onClick = onClick,
        label = {
            Text(
                text = century.label,
                style = MaterialTheme.typography.labelSmall
            )
        },
        shape = CircleShape,
        modifier = modifier
    )
}

@Preview
@Composable
private fun CenturyChipPreview() {
    PoemThemePreview {
        CenturyChip(
            century = CenturyUiModel(
                "شاعران پربازدید",
                false
            ),
            onClick = {}
        )
    }
}
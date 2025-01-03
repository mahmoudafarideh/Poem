package m.a.poem.ui.home.component

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import m.a.poem.ui.home.model.CenturyUiModel
import m.a.poem.ui.theme.PoemTheme

@Composable
fun CenturyChip(
    century: CenturyUiModel,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = century.isSelected,
        onClick = {},
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
    PoemTheme {
        CenturyChip(
            century = CenturyUiModel(
                "شاعران پربازدید",
                false
            )
        )
    }
}
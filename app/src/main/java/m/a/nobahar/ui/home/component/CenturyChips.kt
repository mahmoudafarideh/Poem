package m.a.nobahar.ui.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import m.a.nobahar.ui.home.model.CenturyUiModel
import m.a.nobahar.ui.theme.PoemThemePreview

@Composable
fun CenturyChips(
    labels: ImmutableList<CenturyUiModel>,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        items(
            items = labels,
            key = { it.label }
        ) {
            CenturyChip(it, { onClick(it.label) }, Modifier)
        }
    }
}

@Preview
@Composable
private fun CenturyChipsPreview() {
    PoemThemePreview {
        CenturyChips(
            labels = persistentListOf(
                CenturyUiModel("قرن پنجم", true)
            ),
            onClick = {}
        )
    }
}
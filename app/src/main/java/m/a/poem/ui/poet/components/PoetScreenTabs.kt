package m.a.poem.ui.poet.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import m.a.poem.R
import m.a.poem.ui.poet.model.PoetScreenTabsUiModel

@Composable
internal fun PoetScreenTabs(
    selectedTab: PoetScreenTabsUiModel,
    onTabClick: (PoetScreenTabsUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PoetChip(
            label = stringResource(R.string.poet_books_label),
            isSelected = selectedTab == PoetScreenTabsUiModel.Books,
            onClick = {
                onTabClick(PoetScreenTabsUiModel.Books)
            }
        )
        PoetChip(
            label = stringResource(R.string.poet_bio_label),
            isSelected = selectedTab == PoetScreenTabsUiModel.Biography,
            onClick = {
                onTabClick(PoetScreenTabsUiModel.Biography)
            }
        )
    }
}
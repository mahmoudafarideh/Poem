package m.a.poem.ui.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp


@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun SearchAppBar(
    onTermChange: (String) -> Unit,
    term: String,
    onBackClick: () -> Unit,
    bookName: String?,
    poetName: String?,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            SearchInput(
                term = term,
                onTermChange = onTermChange,
                poetName = poetName,
                bookName = bookName,
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        onBackClick()
                    }
                    .padding(12.dp)
            )
        },
        modifier = modifier
    )
}
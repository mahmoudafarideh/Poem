package m.a.poem.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import m.a.poem.R
import m.a.poem.ui.shared.ui.SabaPreview
import m.a.poem.ui.theme.PoemThemePreview


@Composable
internal fun AppInfoBar(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.mipmap.saba_logo),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = stringResource(R.string.saba_slogan),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.size(12.dp))
        Image(
            painter = painterResource(R.drawable.gdap),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
    }
}

@SabaPreview
@Composable
fun AppInfoBarPreview() {
    PoemThemePreview {
        AppInfoBar()
    }
}
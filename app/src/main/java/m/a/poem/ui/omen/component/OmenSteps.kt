package m.a.poem.ui.omen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import m.a.nobahar.R

@Composable
internal fun OmenSteps(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.omen_first_step_label),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = stringResource(R.string.omen_sentence_label),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )
        Spacer(modifier = Modifier.size(24.dp))
        Text(
            text = stringResource(R.string.omen_final_step_label),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            lineHeight = 26.sp,
        )
    }
}
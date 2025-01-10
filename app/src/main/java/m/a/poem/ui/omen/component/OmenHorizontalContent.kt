package m.a.poem.ui.omen.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import m.a.nobahar.R
import m.a.poem.domain.model.LoadableData
import m.a.poem.ui.omen.model.OmenUiModel

@Composable
internal fun OmenHorizontalContent(
    shouldShowMessageBar: Boolean,
    state: LoadableData<OmenUiModel>,
    onOmenClick: () -> Unit
) {
    val boxWeight by animateFloatAsState(
        if (shouldShowMessageBar) 1f else .01f,
        tween(1_200)
    )
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.omen_header),
            contentDescription = null,
            modifier = Modifier.fillMaxHeight()
        )
        Box(
            modifier = Modifier
                .then(Modifier.weight(boxWeight))
                .background(Color(34, 25, 125))
                .fillMaxHeight()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row {
                OmenSteps(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.size(24.dp))
                OmenImage(state, onOmenClick, Modifier.size(148.dp))
                Spacer(modifier = Modifier.size(24.dp))
            }
        }
        Image(
            painter = painterResource(R.drawable.omen_footer),
            contentDescription = null,
            modifier = Modifier.fillMaxHeight()
        )
        ((1f.minus(boxWeight)).div(2)).takeIf {
            it > 0f
        }?.let {
            Spacer(modifier = Modifier.weight(it))
        }
    }
}
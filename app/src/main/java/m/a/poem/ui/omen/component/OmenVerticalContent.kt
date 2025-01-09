package m.a.poem.ui.omen.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import m.a.poem.R
import m.a.poem.domain.model.LoadableData
import m.a.poem.ui.omen.model.OmenUiModel

@Composable
internal fun OmenVerticalContent(
    shouldShowMessageBar: Boolean,
    state: LoadableData<OmenUiModel>,
    onOmenClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .width(IntrinsicSize.Min)
            .animateContentSize(tween(1_200)),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.omen_header),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .then(
                    if (shouldShowMessageBar) Modifier.weight(1f)
                    else Modifier.height(0.dp)
                )
                .background(Color(34, 25, 125))
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OmenSteps(modifier = Modifier)
                Spacer(modifier = Modifier.size(12.dp))
                OmenImage(state, onOmenClick, Modifier.size(144.dp))
            }
        }
        Image(
            painter = painterResource(R.drawable.omen_footer),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

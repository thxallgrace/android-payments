package nextstep.payments.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PaymentCardLayout(
    backgroundColor : Color,
    modifier: Modifier = Modifier,
    onClick : (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .shadow(8.dp)
            .size(width = 208.dp, height = 124.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(5.dp),
            )
            .then(
                if(onClick == null) Modifier else Modifier.clickable { onClick() }
            )
    ) {
       content()
    }
}

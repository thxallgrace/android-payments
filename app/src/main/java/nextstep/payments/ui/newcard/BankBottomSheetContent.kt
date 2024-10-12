package nextstep.payments.ui.newcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nextstep.payments.ui.model.BankType


private const val COLUMN_COUNT = 4

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BankBottomSheetContent(
    onItemClick: (BankType) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .height(227.dp)
            .padding(vertical = 36.dp),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.spacedBy(23.dp),
        maxItemsInEachRow = COLUMN_COUNT,
    ) {
        BankType.entries.forEach {
            if (it == BankType.NOT_SELECTED) return@forEach
            BankSelectItem(
                bankType = it,
                onClick = onItemClick,
            )
        }
    }
}

@Composable
fun BankSelectItem(
    bankType: BankType,
    onClick: (BankType) -> Unit,
) {
    Column(
        modifier = Modifier
            .width(85.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable { onClick(bankType) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        Image(
            painter = painterResource(id = bankType.getIconRes()!!),
            contentDescription = "description",
            modifier = Modifier
                .size(37.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )
        Text(
            text = stringResource(id = bankType.getNameRes()!!),
            fontSize = 16.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BankBottomSheetContentPreview() {
    BankBottomSheetContent({})
}


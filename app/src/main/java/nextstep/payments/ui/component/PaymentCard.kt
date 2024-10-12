package nextstep.payments.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nextstep.payments.data.model.CreditCard
import nextstep.payments.ui.ext.toFormattedCardNumber
import nextstep.payments.ui.ext.toFormattedDate
import nextstep.payments.ui.model.BankType
import nextstep.payments.ui.theme.Yellow

@Composable
fun PaymentCard(
    modifier: Modifier = Modifier,
    card: CreditCard = CreditCard(),
) {
    PaymentCardLayout(
        modifier = modifier,
        backgroundColor = card.bank.getCardColorRes()
    ) {
        Box(
            modifier = Modifier
                .padding(start = 14.dp, bottom = 10.dp)
                .size(width = 40.dp, height = 26.dp)
                .background(
                    color = Yellow,
                    shape = RoundedCornerShape(4.dp),
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 14.dp, end = 14.dp, top = 15.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            val cardTextStyle = TextStyle(
                fontSize = 12.sp,
                lineHeight = 14.06.sp,
                color = Color.White,
                letterSpacing = 1.sp
            )

            Text(
                text = card.bank.getNameRes()?.let { stringResource(id = it) } ?: "",
                style = cardTextStyle
            )

            if (card.cardNumber.isNotEmpty()) {
                Column {
                    Text(
                        text = card.cardNumber.toFormattedCardNumber(),
                        style = cardTextStyle
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp),
                        horizontalArrangement = Arrangement.Absolute.SpaceBetween
                    ) {
                        Text(text = card.ownerName ?: "CREW", style = cardTextStyle)
                        Text(text = card.expiredDate.toFormattedDate(), style = cardTextStyle)
                    }
                }
            }
        }
    }
}

private class PaymentCardPreviewParameters : PreviewParameterProvider<CreditCard> {
    override val values: Sequence<CreditCard> = sequenceOf(
        CreditCard(),
        CreditCard(
            cardNumber = "123456789011",
            ownerName = "kim",
            expiredDate = "1130",
            bank = BankType.NOT_SELECTED
        ),
        CreditCard(
            cardNumber = "123456789011",
            ownerName = "kim",
            expiredDate = "1130",
            bank = BankType.LOTTE
        )
    )
}


@Preview
@Composable
fun PaymentCardPreview(
    @PreviewParameter(PaymentCardPreviewParameters::class) parameter: CreditCard
) {
    PaymentCard(
        card = parameter
    )
}

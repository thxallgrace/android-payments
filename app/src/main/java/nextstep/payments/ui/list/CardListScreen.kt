package nextstep.payments.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nextstep.payments.R
import nextstep.payments.data.model.CreditCard
import nextstep.payments.ui.component.PaymentCard
import nextstep.payments.ui.component.PaymentCardLayout
import nextstep.payments.ui.model.BankType
import nextstep.payments.ui.theme.DarkGrey
import nextstep.payments.ui.theme.Grey

@Composable
internal fun CardListScreen(
    viewModel: CardListViewModel,
    navigateToNewCardScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val creditCardState by viewModel.creditCardState.collectAsStateWithLifecycle()
    CardListScreen(
        cardState = creditCardState,
        onAddClick = navigateToNewCardScreen,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardListScreen(
    cardState: CreditCardUiState,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                actions = {
                    if (cardState is CreditCardUiState.Many) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .clickable(onClick = onAddClick),
                            text = "추가",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                    }
                },
            )
        },
        modifier = modifier,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (cardState) {
                CreditCardUiState.Empty -> CardEmptyContent(
                    onAddClick = onAddClick,
                    modifier = modifier
                )

                is CreditCardUiState.One -> SingleCardContent(
                    card = cardState.card,
                    onAddClick = onAddClick,
                    modifier = modifier
                )

                is CreditCardUiState.Many -> {
                    LazyColumn(
                        modifier = modifier.padding(top = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(36.dp)
                    ) {
                        items(cardState.cards) { item ->
                            PaymentCard(card = item)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardEmptyContent(
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(top = 32.dp),
    ) {
        Text(
            text = stringResource(id = R.string.card_list_empty_desc),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
        AddCard(
            onClick = onAddClick,
            modifier = modifier.padding(top = 32.dp)
        )
    }
}

@Composable
fun SingleCardContent(
    card: CreditCard,
    onAddClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier.padding(top = 12.dp)
    ) {
        PaymentCard(
            card = card
        )
        AddCard(
            onClick = onAddClick,
            modifier = Modifier.padding(top = 36.dp)
        )
    }
}

@Composable
fun AddCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    PaymentCardLayout(
        backgroundColor = Grey,
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.Center),
            tint = DarkGrey,
            imageVector = Icons.Default.Add,
            contentDescription = "카드 추가"
        )
    }
}

private class CardListScreenPreviewParameters : PreviewParameterProvider<CreditCardUiState> {
    override val values: Sequence<CreditCardUiState> = sequenceOf(
        CreditCardUiState.Empty,
        CreditCardUiState.One(
            card = CreditCard(
                cardNumber = "0000-1111-2222-3333",
                ownerName = "Kim",
                expiredDate = "4/25",
                bank = BankType.SHINHAN
            )
        ),
        CreditCardUiState.Many(
            cards = listOf(
                CreditCard(
                    cardNumber = "0000-1111-2222-3333",
                    ownerName = "Kim",
                    expiredDate = "04/25",
                    bank = BankType.SHINHAN
                ),
                CreditCard(
                    cardNumber = "0000-1111-2222-3333",
                    ownerName = "Park",
                    expiredDate = "04/25",
                    bank = BankType.KB
                ),
                CreditCard(
                    cardNumber = "0000-1111-2222-3333",
                    ownerName = "Song",
                    expiredDate = "04/25",
                    bank = BankType.LOTTE
                ))
        )
    )
}

@Preview
@Composable
fun CardListScreenPreview(
    @PreviewParameter(CardListScreenPreviewParameters::class) cardUiState: CreditCardUiState
) {
    CardListScreen(
        cardState = cardUiState,
        onAddClick = {}
    )
}

package nextstep.payments.ui.newcard

import android.media.tv.TvContract.PreviewPrograms
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import nextstep.payments.R
import nextstep.payments.data.model.CreditCard
import nextstep.payments.ui.component.PaymentCard
import nextstep.payments.ui.model.BankType
import nextstep.payments.ui.theme.PaymentsTheme
import nextstep.payments.ui.utils.CreditCardVisualTransformation
import nextstep.payments.ui.utils.ExpiredDateVisualTransformation

// Stateful
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NewCardScreen(
    isEditMode: Boolean,
    viewModel: NewCardViewModel,
    navigateToCardList: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val cardNumber by viewModel.cardNumber.collectAsStateWithLifecycle()
    val expiredDate by viewModel.expiredDate.collectAsStateWithLifecycle()
    val ownerName by viewModel.ownerName.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val cardAdded by viewModel.cardAdded.collectAsStateWithLifecycle()
    val selectedBank by viewModel.selectedBank.collectAsStateWithLifecycle()
    val enableSaveButton by viewModel.enableSaveButton.collectAsStateWithLifecycle()

    LaunchedEffect(cardAdded) {
        if (cardAdded) navigateToCardList()
    }

    val modalBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded= true,
        confirmValueChange = { false }
    )

    LaunchedEffect(key1 = selectedBank) {
        if (selectedBank != BankType.NOT_SELECTED) {
            modalBottomSheetState.hide()
        }
    }

    NewCardScreen(
        isEditMode = isEditMode,
        cardNumber = cardNumber,
        expiredDate = expiredDate,
        ownerName = ownerName,
        password = password,
        selectedBank = selectedBank,
        setCardNumber = viewModel::setCardNumber,
        setExpiredDate = viewModel::setExpiredDate,
        setOwnerName = viewModel::setOwnerName,
        setPassword = viewModel::setPassword,
        enableSaveButton = enableSaveButton,
        onBackClick = navigateToCardList,
        onSaveClick = if(isEditMode) viewModel::updateCard else viewModel::addCard,
        bottomSheetState = modalBottomSheetState,
        onSelectBank = viewModel::setSelectedBank,
        modifier = modifier
    )
}

// Stateless
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewCardScreen(
    isEditMode: Boolean,
    cardNumber: String,
    expiredDate: String,
    ownerName: String,
    password: String,
    selectedBank: BankType,
    setCardNumber: (String) -> Unit,
    setExpiredDate: (String) -> Unit,
    setOwnerName: (String) -> Unit,
    setPassword: (String) -> Unit,
    enableSaveButton: Boolean,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    bottomSheetState: SheetState,
    onSelectBank: (BankType) -> Unit,
    modifier: Modifier = Modifier
) {
    if(selectedBank == BankType.NOT_SELECTED && !isEditMode) {
        ModalBottomSheet(
            modifier = modifier,
            sheetState = bottomSheetState,
            onDismissRequest = { },
        ) {
            BankBottomSheetContent(
                onItemClick = onSelectBank,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(297.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }

    Scaffold(
        topBar = { NewCardTopBar(
            title = if(isEditMode) {
                stringResource(id = R.string.payment_edit_title)
            } else {
                stringResource(id = R.string.payment_register_title)
            },
            enableSaveButton = enableSaveButton,
            onBackClick = onBackClick,
            onSaveClick = onSaveClick
        )
     },
        modifier = modifier
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(14.dp))

            PaymentCard(
                card = CreditCard().apply {
                    bank = selectedBank
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = cardNumber,
                onValueChange = { newText ->
                    if (newText.length <= 16 && newText.all { it.isDigit() }) {
                        setCardNumber(newText)
                    }
                },
                label = { Text(text = stringResource(id = R.string.payment_card_number_label)) },
                placeholder = { Text(text = stringResource(id = R.string.payment_card_number_placeholder)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = CreditCardVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
            )

            OutlinedTextField(
                value = expiredDate,
                onValueChange = { newText ->
                    if (newText.length <= 4 && newText.all { it.isDigit() }) {
                        setExpiredDate(newText)
                    }
                },
                label = { Text(text = stringResource(id = R.string.payment_card_expired_date_label)) },
                placeholder = { Text(text = stringResource(id = R.string.payment_card_expired_date_placeholder)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = ExpiredDateVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
            )

            OutlinedTextField(
                value = ownerName,
                onValueChange = setOwnerName,
                label = { Text(text = stringResource(id = R.string.payment_card_owner_name_label)) },
                placeholder = { Text(text = stringResource(id = R.string.payment_card_owner_name_placeholder)) },
                modifier = Modifier.fillMaxWidth(),
            )

            OutlinedTextField(
                value = password,
                onValueChange = setPassword,
                label = { Text(text = stringResource(id = R.string.payment_card_password_label)) },
                placeholder = { Text(text = stringResource(id = R.string.payment_card_password_placeholder)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun StatelessNewCardScreenPreview() {
    PaymentsTheme {
        NewCardScreen(
            isEditMode = true,
            cardNumber = "1111222233334444",
            expiredDate = "0000",
            ownerName = "김은혜",
            password = "123123",
            selectedBank = BankType.SHINHAN,
            setCardNumber = {},
            setExpiredDate = {},
            setOwnerName = {},
            setPassword = {},
            enableSaveButton = false,
            onBackClick = {},
            onSaveClick = {},
            bottomSheetState = rememberModalBottomSheetState(),
            onSelectBank = {}
        )
    }
}

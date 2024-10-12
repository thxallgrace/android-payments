package nextstep.payments.ui.newcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import nextstep.payments.data.PaymentCardsRepository
import nextstep.payments.data.model.CreditCard
import nextstep.payments.ui.model.BankType

class NewCardViewModel(
    private val repository: PaymentCardsRepository = PaymentCardsRepository
) : ViewModel() {

    private val _cardNumber = MutableStateFlow("")
    val cardNumber: StateFlow<String> = _cardNumber.asStateFlow()

    private val _expiredDate = MutableStateFlow("")
    val expiredDate: StateFlow<String> = _expiredDate.asStateFlow()

    private val _ownerName = MutableStateFlow("")
    val ownerName: StateFlow<String> = _ownerName.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _cardAdded = MutableStateFlow(false)
    val cardAdded: StateFlow<Boolean> = _cardAdded.asStateFlow()

    private val _selectedBank = MutableStateFlow(BankType.NOT_SELECTED)
    val selectedBank = _selectedBank.asStateFlow()

    private val _originCard = MutableStateFlow(CreditCard())
    val originCard = _originCard.asStateFlow()

    private val _enableSaveButton = MutableStateFlow(true)
    val enableSaveButton = _enableSaveButton.asStateFlow()

    init {
        combine(
            cardNumber,
            expiredDate,
            ownerName,
            password,
            selectedBank
        ) { number, date, name, password, bank ->
            val newCard = CreditCard(number, date, name, password, bank)

            // originCard가 비어있는 경우 수정 모드가 아니므로 저장 버튼 활성화 (true)
            originCard.value.isEmpty() || (newCard != originCard.value)
        }.onEach { isDifferent ->
            _enableSaveButton.value = isDifferent
        }.launchIn(viewModelScope)
    }

    fun setCardNumber(cardNumber: String) {
        _cardNumber.value = cardNumber
    }

    fun setExpiredDate(expiredDate: String) {
        _expiredDate.value = expiredDate
    }

    fun setOwnerName(ownerName: String) {
        _ownerName.value = ownerName
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun addCard() {
        repository.addCard(
            CreditCard(
                cardNumber = cardNumber.value,
                ownerName = ownerName.value,
                expiredDate = expiredDate.value,
                password = password.value,
                bank = selectedBank.value
            )
        )
        _cardAdded.value = true
    }

    fun setSelectCard(selectedCard: CreditCard) {
        _originCard.value = selectedCard

        setCardNumber(selectedCard.cardNumber)
        setExpiredDate(selectedCard.expiredDate)
        setOwnerName(selectedCard.ownerName ?: "")
        setPassword(selectedCard.password ?: "")
        setSelectedBank(selectedCard.bank)
    }

    fun updateCard() {
        if (!enableSaveButton.value) {
            return
        }

        repository.updateCard(
            cardNumber = originCard.value.cardNumber,
            newCard = CreditCard(
                cardNumber = cardNumber.value,
                ownerName = ownerName.value,
                expiredDate = expiredDate.value,
                password = password.value,
                bank = selectedBank.value
            )
        )
        _cardAdded.value = true
    }

    fun setSelectedBank(bankTypeModel: BankType) {
        _selectedBank.value = bankTypeModel
    }
}

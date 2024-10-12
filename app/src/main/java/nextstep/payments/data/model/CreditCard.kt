package nextstep.payments.data.model

import nextstep.payments.ui.model.BankType

data class CreditCard (
    val cardNumber: String = "",
    val ownerName: String? = "",
    val expiredDate: String = "",
    var bank: BankType = BankType.NOT_SELECTED
)



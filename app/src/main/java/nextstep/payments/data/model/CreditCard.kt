package nextstep.payments.data.model

import nextstep.payments.ui.model.BankType
import java.io.Serializable

data class CreditCard (
    val cardNumber: String = "",
    val expiredDate: String = "",
    val ownerName: String? = "",
    val password: String? = "",
    var bank: BankType = BankType.NOT_SELECTED
) : Serializable {

    fun isEmpty(): Boolean {
        return cardNumber == "" &&
                expiredDate == "" &&
                ownerName == "" &&
                password == "" &&
                bank == BankType.NOT_SELECTED
    }
}



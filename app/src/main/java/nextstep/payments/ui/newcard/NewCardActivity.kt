package nextstep.payments.ui.newcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import nextstep.payments.data.model.CreditCard
import nextstep.payments.ui.theme.PaymentsTheme

class NewCardActivity : ComponentActivity() {
    private val viewModel : NewCardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isEditMode = intent.hasExtra("selectedCard")
        if(isEditMode) {
            intent.getSerializableExtra("selectedCard", CreditCard::class.java)?.let {
                viewModel.setSelectCard(it)
            }
        }

        setContent {
            PaymentsTheme {
                NewCardScreen(
                    isEditMode = isEditMode,
                    viewModel = viewModel,
                    navigateToCardList = {
                        setResult(RESULT_OK)
                        finish()
                    }
                )
            }
        }
    }
}

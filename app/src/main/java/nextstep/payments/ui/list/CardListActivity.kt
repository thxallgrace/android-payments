package nextstep.payments.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import nextstep.payments.ui.newcard.NewCardActivity
import nextstep.payments.ui.theme.PaymentsTheme

class CardListActivity : ComponentActivity() {
    private val viewModel: CardListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PaymentsTheme {
                val launcher =
                    rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                        if (it.resultCode == RESULT_OK) {
                            viewModel.fetchCards()
                        }
                    }

                CardListScreen(
                    viewModel = viewModel,
                    navigateToNewCardScreen = {
                        val intent = Intent(this, NewCardActivity::class.java)
                        launcher.launch(intent)
                    },
                    navigateToEditCardScreen = {
                        val intent = Intent(this, NewCardActivity::class.java).apply {
                            putExtra("selectedCard", it)
                        }
                        launcher.launch(intent)
                    }
                )
            }
        }
    }
}

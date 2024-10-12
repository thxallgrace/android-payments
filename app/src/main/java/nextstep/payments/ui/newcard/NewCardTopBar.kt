package nextstep.payments.ui.newcard

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import nextstep.payments.data.model.CreditCard
import nextstep.payments.ui.model.BankType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCardTopBar(
    title: String,
    enableSaveButton: Boolean,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "뒤로 가기",
                )
            }
        },
        actions = {
            IconButton(
                onClick = { if(enableSaveButton) onSaveClick() },
                enabled = enableSaveButton
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "완료",
                )
            }
        },
        modifier = modifier
    )
}

private class NewCardTopBarPreviewParameters : PreviewParameterProvider<Pair<String, Boolean>> {
    override val values: Sequence<Pair<String, Boolean>> = sequenceOf(
        "카드 추가" to true,
        "카드 수정" to false,
    )
}

@Preview
@Composable
fun NewCardTopBarPreview(
    @PreviewParameter(NewCardTopBarPreviewParameters::class) parameter: Pair<String, Boolean>
) {
    NewCardTopBar(
        title = parameter.first,
        enableSaveButton = parameter.second,
        onBackClick = { /*TODO*/ },
        onSaveClick = { /*TODO*/ })
}

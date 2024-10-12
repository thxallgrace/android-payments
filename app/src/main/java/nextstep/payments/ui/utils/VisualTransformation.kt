package nextstep.payments.ui.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CreditCardVisualTransformation : VisualTransformation {
    companion object {
        private const val CREDIT_CARD_MAX_LENGTH = 16
    }

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= CREDIT_CARD_MAX_LENGTH) text.text.substring(0 until CREDIT_CARD_MAX_LENGTH) else text.text
        var out = ""
        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i % 4 == 3 && i != trimmed.lastIndex) out += " - "
        }

        // 텍스트를 변환할 때 커서 위치를 올바르게 유지하도록 매핑
        val creditCardOffsetTranslator = object : OffsetMapping {

            // 원본 텍스트의 offset을 변환된 텍스트에 대응하는 위치로 변환
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 4 -> offset
                    offset <= 8 -> offset + 3
                    offset <= 12 -> offset + 6
                    offset <= CREDIT_CARD_MAX_LENGTH -> offset + 9
                    else -> CREDIT_CARD_MAX_LENGTH + 9
                }
            }

            // 변환된 텍스트의 offset을 원본 텍스트에 대응하는 위치로 변환
            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 3 -> offset
                    offset <= 11 -> offset - 3
                    offset <= 18 -> offset - 6
                    offset <= 25 -> offset - 9
                    else -> CREDIT_CARD_MAX_LENGTH
                }
            }
        }

        return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
    }
}

class ExpiredDateVisualTransformation : VisualTransformation {
    companion object {
        private const val EXPIRED_DATE_MAX_LENGTH = 4
    }

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= EXPIRED_DATE_MAX_LENGTH) text.text.substring(0 until EXPIRED_DATE_MAX_LENGTH) else text.text

        var out = ""
        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i == 1) out += " / "
        }

        val expirationDateOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 1) return offset
                if (offset <= 3) return offset + 3
                return EXPIRED_DATE_MAX_LENGTH + 3
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 2) return offset
                if (offset <= 7) return offset - 3
                return EXPIRED_DATE_MAX_LENGTH
            }
        }

        return TransformedText(AnnotatedString(out), expirationDateOffsetTranslator)
    }
}

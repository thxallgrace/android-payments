package nextstep.payments.ui.model

import androidx.compose.ui.graphics.Color
import nextstep.payments.R
import nextstep.payments.ui.theme.BCBrandColor
import nextstep.payments.ui.theme.Black
import nextstep.payments.ui.theme.HANABrandColor
import nextstep.payments.ui.theme.HYUNDAIBrandColor
import nextstep.payments.ui.theme.KAKAOBANKBrandColor
import nextstep.payments.ui.theme.KBBrandColor
import nextstep.payments.ui.theme.LOTTEBrandColor
import nextstep.payments.ui.theme.SHINHANBrandColor
import nextstep.payments.ui.theme.WOORIBrandColor

enum class BankType {
    NOT_SELECTED, BC, SHINHAN, KAKAOBANK, HYUNDAI, WOORI, LOTTE, HANA, KB;

    fun getIconRes(): Int? {
        return when (this) {
            BC -> R.drawable.ic_bc
            SHINHAN -> R.drawable.ic_shinhan
            KAKAOBANK -> R.drawable.ic_kakaobank
            HYUNDAI -> R.drawable.ic_hyundai
            WOORI -> R.drawable.ic_woori
            LOTTE -> R.drawable.ic_lotte
            HANA -> R.drawable.ic_hana
            KB -> R.drawable.ic_kb
            else -> null
        }
    }

    fun getNameRes(): Int? {
        return when (this) {
            BC -> R.string.bc_card
            SHINHAN -> R.string.shinhan_card
            KAKAOBANK -> R.string.kakao_bank
            HYUNDAI -> R.string.hyundai_card
            WOORI -> R.string.woori_card
            LOTTE -> R.string.lotte_card
            HANA -> R.string.hana_card
            KB -> R.string.kb_card
            else -> null
        }
    }

    fun getCardColorRes(): Color {
        return when(this) {
            BC -> BCBrandColor
            SHINHAN -> SHINHANBrandColor
            KAKAOBANK -> KAKAOBANKBrandColor
            HYUNDAI -> HYUNDAIBrandColor
            WOORI -> WOORIBrandColor
            LOTTE -> LOTTEBrandColor
            HANA -> HANABrandColor
            KB -> KBBrandColor
            else -> Black
        }
    }
}

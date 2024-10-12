package nextstep.payments.ui.ext

fun String.toFormattedCardNumber(): String {
    return this.chunked(4).joinToString("-")
}

fun String.toFormattedDate(): String {
    if (this.length != 4) return this
    val month = this.substring(0, 2)
    val year = this.substring(2, 4)
    return "$month / $year"
}

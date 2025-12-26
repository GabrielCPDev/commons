package models.money

enum class Currency(val value: String) {
    BRL("Brazilian Real"),
    USD("US dollar"),
    BTC("Bitcoin"),
    EUR("euro");

    override fun toString(): String = value
}

package models.money

enum class Currency(val code: String) {
    BRL("BRL"),
    USD("USD"),
    EUR("EUR");

    override fun toString(): String = code
}

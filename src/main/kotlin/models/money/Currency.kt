package models.money

enum class Currency(
    val value: String,
    val scale: Int
) {
    BRL("Brazilian Real", 2),
    USD("US dollar", 2),
    EUR("euro", 2),
    BTC("Bitcoin", 8);

    override fun toString(): String = value
}


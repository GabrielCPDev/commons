package web.pageable

import java.math.BigDecimal

sealed interface FilterValue {
    fun raw(): Any
}

data class StringFilter(val value: String) : FilterValue {
    override fun raw(): String = value
}

data class IntFilter(val value: Int) : FilterValue {
    override fun raw(): Int = value
}

data class LongFilter(val value: Long) : FilterValue {
    override fun raw(): Long = value
}

data class DoubleFilter(val value: Double) : FilterValue {
    override fun raw(): Double = value
}

data class BigDecimalFilter(val value: BigDecimal) : FilterValue {
    override fun raw(): BigDecimal = value
}

package io.csv.utils

object CsvValidators {

    fun maxLength(max: Int): (Any?) -> String? = {
        val value = it as? String
        if (value != null && value.length > max)
            "Must be at most $max characters"
        else null
    }

    fun min(min: Int): (Any?) -> String? = {
        val value = it as? Int
        if (value != null && value < min)
            "Must be >= $min"
        else null
    }

    val noValidation: (Any?) -> String? = { null }
}

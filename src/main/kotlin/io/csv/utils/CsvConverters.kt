package io.csv.utils

object CsvConverters {

    val string: (String) -> Any? = { it }

    val int: (String) -> Any? = {
        it.toIntOrNull()
            ?: throw IllegalArgumentException("Must be INT")
    }

    val double: (String) -> Any? = {
        it.toDoubleOrNull()
            ?: throw IllegalArgumentException("Must be DOUBLE")
    }

    val boolean: (String) -> Any? = {
        it.toBooleanStrictOrNull()
            ?: throw IllegalArgumentException("Must be BOOLEAN")
    }
}

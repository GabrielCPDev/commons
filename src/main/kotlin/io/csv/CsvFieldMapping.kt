package io.csv

data class CsvFieldMapping(
    val columnIndex: Int,
    val fieldName: String,
    val required: Boolean = false,
    val converter: (String) -> Any? = { null },
    val validator: (Any?) -> String? = { null }
)

package io.csv

data class CsvTemplate(
    val separator: Char,
    val hasHeader: Boolean,
    val fields: List<CsvFieldMapping>
)
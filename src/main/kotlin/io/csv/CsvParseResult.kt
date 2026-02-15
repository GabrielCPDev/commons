package io.csv

data class CsvParseResult<T>(
    val success: T? = null,
    val rawLine: String,
    val errors: List<String> = emptyList(),
    val lineNumber: Int
)
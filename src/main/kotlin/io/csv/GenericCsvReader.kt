package io.csv

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class GenericCsvReader(
    private val concurrency: Int = Runtime.getRuntime().availableProcessors(),
    private val bufferCapacity: Int = Channel.BUFFERED,
    private val onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun <T> read(
        input: InputStream,
        template: CsvTemplate,
        mapper: (Map<String, Any?>) -> T
    ): Flow<CsvParseResult<T>> {

        return flow {
            BufferedReader(InputStreamReader(input)).use { reader ->

                var lineNumber = if (template.hasHeader) 0 else 1

                if (template.hasHeader) {
                    reader.readLine()
                }

                reader.lineSequence().forEach { line ->
                    lineNumber++
                    emit(lineNumber to line)
                }
            }
        }
            .buffer(
                capacity = bufferCapacity,
                onBufferOverflow = onBufferOverflow
            )
            .flatMapMerge(concurrency) { (lineNumber, line) ->
                flowOf(parseLine(lineNumber, line, template, mapper))
            }
            .flowOn(dispatcher)
    }

    private fun <T> parseLine(
        lineNumber: Int,
        line: String,
        template: CsvTemplate,
        mapper: (Map<String, Any?>) -> T
    ): CsvParseResult<T> {

        val parts = splitLine(line, template.separator)

        val (parsedValues, errors) = validateAndConvertFields(parts, template)

        if (errors.isNotEmpty()) {
            return buildErrorResult(lineNumber, line, errors)
        }

        return buildSuccessResult(lineNumber, line, parsedValues, mapper)
    }

    private fun splitLine(
        line: String,
        separator: Char
    ): List<String> {
        return line.split(separator)
    }

    private fun validateAndConvertFields(
        parts: List<String>,
        template: CsvTemplate
    ): Pair<Map<String, Any?>, List<String>> {

        val errors = mutableListOf<String>()
        val parsedValues = mutableMapOf<String, Any?>()

        template.fields.forEach { field ->

            val rawValue = parts.getOrNull(field.columnIndex)?.trim()

            val fieldError = validateField(field, rawValue)

            if (fieldError != null) {
                errors.add(fieldError)
                return@forEach
            }

            val conversionResult = convertField(field, rawValue)

            if (conversionResult.error != null) {
                errors.add(conversionResult.error)
            } else {
                parsedValues[field.fieldName] = conversionResult.value
            }
        }

        return parsedValues to errors
    }

    private fun validateField(
        field: CsvFieldMapping,
        rawValue: String?
    ): String? {

        if (field.required && rawValue.isNullOrBlank()) {
            return "Field '${field.fieldName}' is required"
        }

        return null
    }

    private fun convertField(
        field: CsvFieldMapping,
        rawValue: String?
    ): ConversionResult {

        if (rawValue.isNullOrBlank()) {
            return ConversionResult(value = null)
        }

        return try {
            val converted = field.converter(rawValue)

            val validationError = field.validator(converted)

            if (validationError != null) {
                ConversionResult(
                    error = "Field '${field.fieldName}': $validationError"
                )
            } else {
                ConversionResult(value = converted)
            }

        } catch (e: Exception) {
            ConversionResult(
                error = "Field '${field.fieldName}' conversion error: ${e.message}"
            )
        }
    }


    private fun <T> buildErrorResult(
        lineNumber: Int,
        line: String,
        errors: List<String>
    ): CsvParseResult<T> {

        return CsvParseResult(
            success = null,
            rawLine = line,
            errors = errors,
            lineNumber = lineNumber
        )
    }

    private fun <T> buildSuccessResult(
        lineNumber: Int,
        line: String,
        parsedValues: Map<String, Any?>,
        mapper: (Map<String, Any?>) -> T
    ): CsvParseResult<T> {

        return try {
            CsvParseResult(
                success = mapper(parsedValues),
                rawLine = line,
                lineNumber = lineNumber
            )
        } catch (e: Exception) {
            CsvParseResult(
                success = null,
                rawLine = line,
                errors = listOf("Mapping error: ${e.message}"),
                lineNumber = lineNumber
            )
        }
    }

    private data class ConversionResult(
        val value: Any? = null,
        val error: String? = null
    )
}

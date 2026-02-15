package io.csv

import io.csv.utils.CsvConverters
import io.csv.utils.CsvValidators
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue


class GenericCsvReaderUnitTest {

    private val reader = GenericCsvReader(
        concurrency = 1,
        bufferCapacity = 10
    )

    private val template = CsvTemplate(
        separator = ';',
        hasHeader = true,
        fields = listOf(
            CsvFieldMapping(
                columnIndex = 0,
                fieldName = "barcode",
                required = true,
                converter = CsvConverters.string,
                validator = CsvValidators.maxLength(20)
            ),
            CsvFieldMapping(
                columnIndex = 1,
                fieldName = "qty",
                required = true,
                converter = CsvConverters.int,
                validator = CsvValidators.min(0)
            )
        )
    )

    data class Product(
        val barcode: String,
        val qty: Int
    )

    private val mapper: (Map<String, Any?>) -> Product = { values ->
        Product(
            barcode = values["barcode"] as String,
            qty = values["qty"] as Int
        )
    }

    private fun inputOf(content: String) =
        ByteArrayInputStream(content.toByteArray(StandardCharsets.UTF_8))

    @Test
    fun `should parse valid line successfully`() = runTest {

        val csv = """
            barcode;qty
            123;10
        """.trimIndent()

        val results = reader.read(inputOf(csv), template, mapper).toList()

        assertEquals(1, results.size)
        assertNotNull(results.first().success)
        assertTrue(results.first().errors.isEmpty())
    }

    @Test
    fun `should return error when integer conversion fails`() = runTest {

        val csv = """
            barcode;qty
            123;abc
        """.trimIndent()

        val result = reader.read(inputOf(csv), template, mapper).toList().first()

        assertNull(result.success)
        assertTrue(result.errors.any { it.contains("Must be INT") })
    }

    @Test
    fun `should return error when validation rule fails`() = runTest {

        val csv = """
            barcode;qty
            123;-5
        """.trimIndent()

        val result = reader.read(inputOf(csv), template, mapper).toList().first()

        assertNull(result.success)
        assertTrue(result.errors.any { it.contains(">= 0") })
    }

    @Test
    fun `should assign correct line number`() = runTest {

        val csv = """
            barcode;qty
            123;10
            456;20
        """.trimIndent()

        val results = reader.read(inputOf(csv), template, mapper).toList()

        assertEquals(1, results[0].lineNumber)
        assertEquals(2, results[1].lineNumber)
    }

    @Test
    fun `should return error when required field is missing`() = runTest {
        val csv = """
        barcode;qty
        ;10
    """.trimIndent()

        val result = reader.read(inputOf(csv), template, mapper).toList().first()

        assertNull(result.success)

        assertTrue(result.errors.any { it.contains("required") })
    }

}
package models.address

import models.Name

data class Address(
    val street: Name,
    val neighborhood: Name?,
    val city: Name,
    val state: Name,
    val zipCode: ZipCode,
    val country: Country,
    val number: String,
    val complement: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)
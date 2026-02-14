package models.address

import models.Name

data class Address(
    val street: Name,
    val neighborhood: Name? = null,
    val city: Name,
    val state: Name,
    val zipCode: ZipCode,
    val country: Country,
    val number: String,
    val complement: String? = null,
    val coordinates: Coordinates? = null
)
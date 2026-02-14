package models.address

import models.Name

data class Country(
    val name: Name,
    val isoCode: String,
    val prefix: String
) {
    init {
        require(isoCode.length == 2) { "ISO Code must be 2 characters (ISO 3166-1 alpha-2)" }
        require(prefix.isNotEmpty() && prefix.length <= 4) { "Invalid country calling code" }
        require(isoCode == isoCode.uppercase()) { "ISO Code must be uppercase" }
    }

    fun getFullPrefix() = "+$prefix"
}
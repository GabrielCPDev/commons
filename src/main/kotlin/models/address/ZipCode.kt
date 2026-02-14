package models.address

@JvmInline
value class ZipCode(val value: String) {
    init {
        require(value.isNotBlank()) { "Zip code cannot be empty" }
        require(value.length >= 3) { "Invalid zip code: $value" }
    }

    fun normalized() = value.replace(Regex("[^A-Za-z0-9]"), "")
}
package models.address

@JvmInline
value class ZipCode(val value: String) {
    init {
        require(value.isNotBlank()) { "Zip code cannot be empty" }
    }

    fun normalized() = value.replace(Regex("[^A-Za-z0-9]"), "")
}

package models.hating

@JvmInline
value class Stars private constructor(
    val value: Int
) {
    init {
        require(value in 1..5) {
            "Rating stars must be between 1 and 5"
        }
    }

    companion object {
        fun of(value: Int): Stars = Stars(value)
    }
}

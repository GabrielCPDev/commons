package models.hating

import java.math.BigDecimal
import java.math.RoundingMode

@ConsistentCopyVisibility
data class Rating private constructor(
    val totalReviews: Int,
    val totalStars: Int
) {

    init {
        require(totalReviews >= 0) { "Total reviews cannot be negative" }
        require(totalStars >= 0) { "Total stars cannot be negative" }
        require(
            totalReviews == 0 || totalStars >= totalReviews
        ) { "Invalid rating state" }
    }

    fun add(stars: Stars): Rating =
        Rating(
            totalReviews = totalReviews + 1,
            totalStars = totalStars + stars.value
        )

    fun average(): BigDecimal =
        if (totalReviews == 0)
            BigDecimal.ZERO
        else
            BigDecimal(totalStars)
                .divide(BigDecimal(totalReviews), 2, RoundingMode.HALF_UP)

    companion object {
        fun initial(): Rating =
            Rating(
                totalReviews = 0,
                totalStars = 0
            )

        fun reconstruct(
            totalReviews: Int,
            totalStars: Int
        ): Rating =
            Rating(totalReviews, totalStars)
    }
}

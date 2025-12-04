package web.pageable

data class SortField(
    val property: String,
    val direction: SortDirection = SortDirection.ASC
)
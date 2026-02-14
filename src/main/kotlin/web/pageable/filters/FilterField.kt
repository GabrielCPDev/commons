package web.pageable.filters

data class FilterField(
    val property: String,
    val value: String,
    val operator: FilterOperator = FilterOperator.CONTAINS
)
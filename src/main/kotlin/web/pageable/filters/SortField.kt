package web.pageable.filters

import web.pageable.SortDirection

data class SortField(
    val property: String,
    val direction: SortDirection = SortDirection.ASC
)
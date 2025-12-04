package web.pageable

data class Pagination(
    val page: Int = 0,
    val size: Int = 20,
    val sort: List<SortField> = emptyList()
) {
    fun direction(): String {
        val dir = sort.firstOrNull()?.direction ?: SortDirection.ASC
        return if (dir == SortDirection.DESC) "DESC" else "ASC"
    }

    companion object {
        fun of(page: Int, size: Int, sort: List<String>?): Pagination {
            val sortFields = sort?.map { param ->
                val parts = param.split(",")
                SortField(
                    property = parts[0],
                    direction = if (parts.getOrNull(1)?.equals("desc", ignoreCase = true) == true)
                        SortDirection.DESC
                    else
                        SortDirection.ASC
                )
            } ?: emptyList()

            return Pagination(page = page, size = size, sort = sortFields)
        }
    }
}
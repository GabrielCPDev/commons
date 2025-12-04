package web.pageable

data class PageResult<T>(
    val content: List<T>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int
){
    fun <R> map(mapper: (T) -> R): PageResult<R> =
        PageResult(
            content = this.content.map(mapper),
            page = this.page,
            size = this.size,
            totalElements = this.totalElements,
            totalPages = this.totalPages
        )
}
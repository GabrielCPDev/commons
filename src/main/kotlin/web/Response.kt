package web

data class Response<T>(
    val data: T? = null,
    val errors: List<String> = emptyList(),
    val success: Boolean = true,
    val code: Int = 200
){
    companion object {
        fun <T> success(data: T? = null): Response<T> = Response(data = data, success = true, code = 200)

        fun <T> error(errors: List<String>, code: Int = 500): Response<T> =
            Response(data = null, errors = errors, success = false, code = code)
    }
}
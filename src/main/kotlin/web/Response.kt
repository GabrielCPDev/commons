package web

data class Response<T>(
    val data: T? = null,
    val errors: List<String> = emptyList(),
    val success: Boolean = true,
    val code: Int = 200
){
    companion object {

        fun <T> success(
            data: T? = null,
            code: Int = 200
        ): Response<T> =
            Response(
                data = data,
                errors = emptyList(),
                success = true,
                code = code
            )

        fun <T> created(data: T? = null): Response<T> =
            success(data = data, code = 201)

        fun <T> noContent(): Response<T> =
            success(data = null, code = 204)

        fun <T> error(
            errors: List<String>,
            code: Int = 500
        ): Response<T> =
            Response(
                data = null,
                errors = errors,
                success = false,
                code = code
            )
    }
}
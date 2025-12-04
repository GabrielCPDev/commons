package validators

interface Validatable<T> {
    fun validate(obj: T)
}
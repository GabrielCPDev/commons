package models.useCases

interface UseCase<IN, OUT> {
    suspend fun execute(input: IN): OUT
}
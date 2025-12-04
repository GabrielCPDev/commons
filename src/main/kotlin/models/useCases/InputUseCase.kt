package models.useCases

interface InputUseCase<IN> {
    suspend fun execute(input: IN)
}
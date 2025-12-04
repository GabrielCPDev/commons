package models.useCases

interface OutputUseCase<OUT> {
    suspend fun execute(): OUT
}
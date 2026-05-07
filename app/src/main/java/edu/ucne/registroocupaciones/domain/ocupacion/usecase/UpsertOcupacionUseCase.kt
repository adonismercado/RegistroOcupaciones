package edu.ucne.registroocupaciones.domain.ocupacion.usecase

import edu.ucne.registroocupaciones.domain.ocupacion.model.Ocupacion
import edu.ucne.registroocupaciones.domain.ocupacion.repository.OcupacionRepository

class UpsertOcupacionUseCase (
    private val repository: OcupacionRepository
) {
    suspend operator fun invoke(ocupacion: Ocupacion) : Result<Int> {
        val descriptionResult = validateDescripcion(ocupacion.descripcion)
        if (!descriptionResult.isValid) {
            return Result.failure(IllegalArgumentException(descriptionResult.error))
        }

        val sueldoResult = validateSueldo(ocupacion.sueldo)
        if (!sueldoResult.isValid) {
            return Result.failure(IllegalArgumentException(sueldoResult.error))
        }

        return runCatching { repository.upsert(ocupacion) }
    }
}
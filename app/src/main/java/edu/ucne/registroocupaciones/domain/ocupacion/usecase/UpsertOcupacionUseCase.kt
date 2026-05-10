package edu.ucne.registroocupaciones.domain.ocupacion.usecase

import edu.ucne.registroocupaciones.domain.ocupacion.model.Ocupacion
import edu.ucne.registroocupaciones.domain.ocupacion.repository.OcupacionRepository
import javax.inject.Inject

class UpsertOcupacionUseCase @Inject constructor(
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

        if (repository.existeDescripcion(ocupacion.descripcion, ocupacion.ocupacionId?: 0)) {
            return Result.failure(IllegalArgumentException("Ya existe una ocupacion con esa descripcion."))
        }
        return runCatching { repository.upsert(ocupacion) }
    }
}
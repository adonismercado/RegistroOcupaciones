package edu.ucne.registroocupaciones.domain.empleado.usecase.empleados

import edu.ucne.registroocupaciones.domain.empleado.model.Empleado
import edu.ucne.registroocupaciones.domain.empleado.repository.EmpleadoRepository
import javax.inject.Inject

class UpsertEmpleadoUseCase @Inject constructor(
    private val repository: EmpleadoRepository
) {
    suspend operator fun invoke(empleado: Empleado): Result<Int> {
        val nombreResult = validateNombre(empleado.nombres)
        if (!nombreResult.isValid) {
            return Result.failure(IllegalArgumentException(nombreResult.error))
        }

        val fechaResult = validateFecha(empleado.fechaIngreso)
        if (!fechaResult.isValid) {
            return Result.failure(IllegalArgumentException(fechaResult.error))
        }

        val sueldoResult = validateSueldo(empleado.sueldo)
        if (!sueldoResult.isValid) {
            return Result.failure(IllegalArgumentException(sueldoResult.error))
        }

        return runCatching { repository.upsert(empleado) }
    }
}
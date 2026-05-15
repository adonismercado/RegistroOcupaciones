package edu.ucne.registroocupaciones.domain.ocupacion.usecase.empleados

import edu.ucne.registroocupaciones.domain.ocupacion.model.Empleado
import edu.ucne.registroocupaciones.domain.ocupacion.repository.EmpleadoRepository
import javax.inject.Inject

class DeleteEmpleadoUseCase @Inject constructor(
    private val repository: EmpleadoRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}
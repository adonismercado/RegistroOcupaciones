package edu.ucne.registroocupaciones.domain.ocupacion.usecase.empleados

import edu.ucne.registroocupaciones.domain.ocupacion.model.Empleado
import edu.ucne.registroocupaciones.domain.ocupacion.repository.EmpleadoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveEmpleadoUseCase @Inject constructor(
    private val repository: EmpleadoRepository
) {
    operator fun invoke(): Flow<List<Empleado>> = repository.observeEmpleados()
}
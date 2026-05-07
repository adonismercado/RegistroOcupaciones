package edu.ucne.registroocupaciones.domain.ocupacion.usecase

import edu.ucne.registroocupaciones.domain.ocupacion.model.Ocupacion
import edu.ucne.registroocupaciones.domain.ocupacion.repository.OcupacionRepository
import kotlinx.coroutines.flow.Flow

class ObserveOcupacionUseCase (
    private val repository: OcupacionRepository
) {
    operator fun invoke(): Flow<List<Ocupacion>> = repository.observeOcupaciones()
}
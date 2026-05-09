package edu.ucne.registroocupaciones.domain.ocupacion.usecase

import edu.ucne.registroocupaciones.domain.ocupacion.model.Ocupacion
import edu.ucne.registroocupaciones.domain.ocupacion.repository.OcupacionRepository
import javax.inject.Inject

class GetOcupacionUseCase @Inject constructor(
    private val repository: OcupacionRepository
) {
    suspend operator fun invoke(id: Int): Ocupacion? = repository.getOcupacion(id)
}
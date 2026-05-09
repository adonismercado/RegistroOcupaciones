package edu.ucne.registroocupaciones.domain.ocupacion.usecase

import edu.ucne.registroocupaciones.domain.ocupacion.repository.OcupacionRepository
import javax.inject.Inject

class DeleteOcupacionUseCase @Inject constructor(
    private val repository: OcupacionRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}
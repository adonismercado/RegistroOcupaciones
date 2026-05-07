package edu.ucne.registroocupaciones.domain.ocupacion.usecase

import edu.ucne.registroocupaciones.domain.ocupacion.repository.OcupacionRepository

class DeleteOcupacionUseCase (
    private val repository: OcupacionRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}
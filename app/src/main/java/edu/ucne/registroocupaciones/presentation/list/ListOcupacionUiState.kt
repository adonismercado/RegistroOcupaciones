package edu.ucne.registroocupaciones.presentation.list

import edu.ucne.registroocupaciones.domain.ocupacion.model.Ocupacion

data class ListOcupacionUiState (
    val isLoading: Boolean = false,
    val ocupaciones: List<Ocupacion> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null,
    val error: String? = null
)
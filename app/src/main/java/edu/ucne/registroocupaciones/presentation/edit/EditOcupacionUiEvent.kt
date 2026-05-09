package edu.ucne.registroocupaciones.presentation.edit

sealed interface EditOcupacionUiEvent {
    data class Load(val id: Int?) : EditOcupacionUiEvent
    data class DescriptionChanged(val value: String) : EditOcupacionUiEvent
    data class SueldoChanged(val value: Double) : EditOcupacionUiEvent
    data object Save : EditOcupacionUiEvent
    data object Delete : EditOcupacionUiEvent
}
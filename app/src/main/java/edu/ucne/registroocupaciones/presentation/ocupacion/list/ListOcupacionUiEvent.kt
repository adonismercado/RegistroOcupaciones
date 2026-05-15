package edu.ucne.registroocupaciones.presentation.ocupacion.list

sealed interface ListOcupacionUiEvent {
    object Load : ListOcupacionUiEvent
    data class Delete(val id: Int) : ListOcupacionUiEvent
    data object CreateNew : ListOcupacionUiEvent
    data class ShowMessage(val message: String) : ListOcupacionUiEvent
    data class Edit(val id: Int) : ListOcupacionUiEvent
}
package edu.ucne.registroocupaciones.presentation.empleado.list

sealed interface ListEmpleadoUiEvent {
    object Load : ListEmpleadoUiEvent
    data class Delete(val id: Int) : ListEmpleadoUiEvent
    data object CreateNew : ListEmpleadoUiEvent
    data class ShowMessage(val message: String) : ListEmpleadoUiEvent
    data class Edit(val id: Int) : ListEmpleadoUiEvent
}
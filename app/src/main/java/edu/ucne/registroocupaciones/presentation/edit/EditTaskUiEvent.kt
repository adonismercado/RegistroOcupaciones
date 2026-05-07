package edu.ucne.registroocupaciones.presentation.edit

sealed interface EditTaskUiEvent {
    data class Load(val id: Int?) : EditTaskUiEvent
    data class DescriptionChanged(val value: String) : EditTaskUiEvent
    data class SueldoChanged(val value: Double) : EditTaskUiEvent
    data object Save : EditTaskUiEvent
    data object Delete : EditTaskUiEvent
}
package edu.ucne.registroocupaciones.presentation.edit

data class EditTaskUiState (
    val ocupacionId: Int? = null,
    val descripcion: String = "",
    val sueldo: Double,
    val descripcionError: String? = null,
    val sueldoError: String? = null,
    val isSaving: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = true,
    val deleted: Boolean = false
)
package edu.ucne.registroocupaciones.presentation.edit

data class EditOcupacionUiState (
    val ocupacionId: Int? = null,
    val descripcion: String = "",
    val sueldo: String = "",
    val descripcionError: String? = null,
    val sueldoError: String? = null,
    val isSaving: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = true,
    val deleted: Boolean = false,
    val isDeleting: Boolean = false
)
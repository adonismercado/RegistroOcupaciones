package edu.ucne.registroocupaciones.presentation.empleado.edit

import edu.ucne.registroocupaciones.presentation.ocupacion.edit.EditOcupacionUiEvent
import java.time.LocalDate

sealed interface EditEmpleadoUiEvent {
    data class Load(val id: Int?): EditEmpleadoUiEvent
    data class NombreChanged(val value: String?): EditEmpleadoUiEvent
    data class FechaIngresoChanged(val value: LocalDate?): EditEmpleadoUiEvent
    data class SexoChanged(val value: String?): EditEmpleadoUiEvent
    data class SueldoChanged(val value: String?): EditEmpleadoUiEvent
    data object Save: EditEmpleadoUiEvent
    data object Delete: EditEmpleadoUiEvent
}
package edu.ucne.registroocupaciones.presentation.empleado.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroocupaciones.domain.empleado.model.Empleado
import edu.ucne.registroocupaciones.domain.ocupacion.usecase.empleados.DeleteEmpleadoUseCase
import edu.ucne.registroocupaciones.domain.ocupacion.usecase.empleados.GetEmpleadoUseCase
import edu.ucne.registroocupaciones.domain.ocupacion.usecase.empleados.UpsertEmpleadoUseCase
import edu.ucne.registroocupaciones.domain.ocupacion.usecase.empleados.validateFecha
import edu.ucne.registroocupaciones.domain.ocupacion.usecase.empleados.validateNombre
import edu.ucne.registroocupaciones.domain.ocupacion.usecase.empleados.validateSueldo
import edu.ucne.registroocupaciones.presentation.navigation.Screen
import edu.ucne.registroocupaciones.presentation.ocupacion.edit.EditOcupacionUiEvent
import edu.ucne.registroocupaciones.presentation.ocupacion.list.ListOcupacionUiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.future.future
import kotlinx.coroutines.launch
import kotlinx.coroutines.newCoroutineContext
import javax.inject.Inject

@HiltViewModel
class EditEmpleadoViewModel @Inject constructor(
    private val getEmpleadoUseCase: GetEmpleadoUseCase,
    private val upsertEmpleadoUseCase: UpsertEmpleadoUseCase,
    private val deleteEmpleadoUseCase: DeleteEmpleadoUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val routeArgs = savedStateHandle.toRoute<Screen.EmpleadoForm>()
    private val empleadoId: Int = routeArgs.empleadoId
    private val _state = MutableStateFlow(EditEmpleadoUiState())
    val state: StateFlow<EditEmpleadoUiState> = _state.asStateFlow()

    init {
        onLoad(empleadoId)
    }

    fun onEvent(event: EditEmpleadoUiEvent) {
        when (event) {
            is EditEmpleadoUiEvent.Load -> onLoad(event.id)
            is EditEmpleadoUiEvent.FechaIngresoChanged -> _state.update {
                it.copy(fechaIngreso = event.value, fechaIngresoError = null)
            }
            is EditEmpleadoUiEvent.NombresChanged -> _state.update {
                it.copy(nombres = event.value, nombresError = null)
            }
            is EditEmpleadoUiEvent.SexoChanged -> _state.update {
                it.copy(sexo = event.value, sexoError = null)
            }
            is EditEmpleadoUiEvent.SueldoChanged -> _state.update {
                it.copy(sueldo = event.value, sueldoError = null)
            }

            EditEmpleadoUiEvent.Save -> onSave()
            EditEmpleadoUiEvent.Delete -> onDelete()
        }

    }

    private fun onLoad(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, empleadoId = null) }
            return
        }

        viewModelScope.launch {
            val empleado = getEmpleadoUseCase(id)
            if (empleado != null) {
                _state.update {
                    it.copy(
                        isNew = false,
                        empleadoId = empleado.empleadoId,
                        fechaIngreso = empleado.fechaIngreso,
                        nombres = empleado.nombres,
                        sexo = empleado.sexo,
                        sueldo = empleado.sueldo
                    )
                }
            } else {
                _state.update { it.copy(isNew = true, empleadoId = null) }
            }
        }
    }

    private fun onSave() {
        val nombresValidation = validateNombre(state.value.nombres)
        val sueldoValidation = validateSueldo(state.value.sueldo)
        val fechaIngresoValidation = validateFecha(state.value.fechaIngreso)

        if (!nombresValidation.isValid || !sueldoValidation.isValid || !fechaIngresoValidation.isValid) {
            _state.update {
                it.copy(
                    fechaIngresoError = fechaIngresoValidation.error ?: "",
                    nombresError = nombresValidation.error ?: "",
                    sueldoError = sueldoValidation.error ?: ""
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            val empleado = Empleado(
                empleadoId = state.value.empleadoId ?: 0,
                fechaIngreso = state.value.fechaIngreso!!,
                nombres = state.value.nombres,
                sexo = state.value.sexo,
                sueldo = state.value.sueldo
            )

            val result = upsertEmpleadoUseCase(empleado)
            result.onSuccess { newId ->
                _state.update {
                    it.copy(
                        isSaving = false,
                        saved = true,
                        empleadoId = newId,
                        isNew = false
                    )
                }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isSaving = false,
                        nombresError = error.message ?: "Error desconocido."
                    )
                }
            }
        }
    }

    private fun onDelete() {
        val id = state.value.empleadoId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteEmpleadoUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}
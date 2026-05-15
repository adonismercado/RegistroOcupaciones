package edu.ucne.registroocupaciones.presentation.empleado.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import edu.ucne.registroocupaciones.domain.ocupacion.usecase.empleados.DeleteEmpleadoUseCase
import edu.ucne.registroocupaciones.domain.ocupacion.usecase.empleados.GetEmpleadoUseCase
import edu.ucne.registroocupaciones.domain.ocupacion.usecase.empleados.UpsertEmpleadoUseCase
import edu.ucne.registroocupaciones.presentation.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

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
}
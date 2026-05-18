package edu.ucne.registroocupaciones.presentation.empleado.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroocupaciones.domain.empleado.usecase.empleados.DeleteEmpleadoUseCase
import edu.ucne.registroocupaciones.domain.empleado.usecase.empleados.ObserveEmpleadoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListEmpleadoViewModel @Inject constructor(
    private val observeEmpleadoUseCase: ObserveEmpleadoUseCase,
    private val deleteEmpleadoUseCase: DeleteEmpleadoUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ListEmpleadoUiState(isLoading = true))
    val state: StateFlow<ListEmpleadoUiState> = _state.asStateFlow()

    init {
        onLaod()
    }

    fun onEvent(event: ListEmpleadoUiEvent) {
        when(event) {
            ListEmpleadoUiEvent.Load -> onLaod()
            is ListEmpleadoUiEvent.Delete -> onDelete(event.id)
            is ListEmpleadoUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
            ListEmpleadoUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is ListEmpleadoUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
        }
    }

    fun onLaod() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            observeEmpleadoUseCase().collectLatest { list ->
                _state.update { it.copy(isLoading = false, empleados = list, message = null) }
            }
        }
    }

    fun onDelete(id: Int) {
        viewModelScope.launch {
            deleteEmpleadoUseCase(id)
            onEvent(ListEmpleadoUiEvent.ShowMessage("Eliminado"))
        }
    }


}
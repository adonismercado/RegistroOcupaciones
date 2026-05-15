package edu.ucne.registroocupaciones.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroocupaciones.domain.ocupacion.usecase.ocupaciones.DeleteOcupacionUseCase
import edu.ucne.registroocupaciones.domain.ocupacion.usecase.ocupaciones.ObserveOcupacionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOcupacionViewModel @Inject constructor(
    private val observeOcupacionUseCase: ObserveOcupacionUseCase,
    private val deleteOcupacionUseCase: DeleteOcupacionUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ListOcupacionUiState(isLoading = true))
    val state: StateFlow<ListOcupacionUiState> = _state.asStateFlow()

    init {
        onLoad()
    }

    fun onEvent(event: ListOcupacionUiEvent) {
        when(event) {
            ListOcupacionUiEvent.Load -> onLoad()
            is ListOcupacionUiEvent.Delete -> onDelete(event.id)
            is ListOcupacionUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
            ListOcupacionUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is ListOcupacionUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
        }
    }
    fun onLoad() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            observeOcupacionUseCase().collectLatest { list ->
                _state.update { it.copy(isLoading = false, ocupaciones = list, message = null) }
            }
        }
    }

    fun onDelete(id: Int) {
        viewModelScope.launch {
            deleteOcupacionUseCase(id)
            onEvent(ListOcupacionUiEvent.ShowMessage("Eliminado"))
        }
    }
}
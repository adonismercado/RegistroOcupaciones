package edu.ucne.registroocupaciones.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroocupaciones.domain.ocupacion.usecase.DeleteOcupacionUseCase
import edu.ucne.registroocupaciones.domain.ocupacion.usecase.ObserveOcupacionUseCase
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

    fun onLoad() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            observeOcupacionUseCase().collectLatest { list ->
                _state.update { it.copy(isLoading = false, ocupaciones = list, message = null) }
            }
        }
    }
}
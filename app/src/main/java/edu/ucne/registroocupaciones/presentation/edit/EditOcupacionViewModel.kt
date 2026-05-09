package edu.ucne.registroocupaciones.presentation.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.registroocupaciones.domain.ocupacion.usecase.DeleteOcupacionUseCase
import edu.ucne.registroocupaciones.domain.ocupacion.usecase.GetOcupacionUseCase
import edu.ucne.registroocupaciones.domain.ocupacion.usecase.UpsertOcupacionUseCase
import edu.ucne.registroocupaciones.domain.ocupacion.model.Ocupacion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditOcupacionViewModel (
    private val getOcupacionUseCase: GetOcupacionUseCase,
    private val upsertOcupacionUseCase: UpsertOcupacionUseCase,
    private val deleteOcupacionUseCase: DeleteOcupacionUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(EditOcupacionUiState())

    val state: StateFlow<EditOcupacionUiState> = _state.asStateFlow()

    private fun onLoad(id: Int?) {
        if(id == null || id == 0) {
            _state.update { it.copy(isNew = true, ocupacionId = null) }
            return
        }

        viewModelScope.launch {
            val ocupacion = getOcupacionUseCase(id)
            if (ocupacion != null) {
                _state.update {
                    it.copy(
                        isNew = false,
                        ocupacionId = ocupacion.ocupacionId,
                        descripcion = ocupacion.descripcion,
                        sueldo = ocupacion.sueldo
                    )
                }
            } else {
                _state.update { it.copy(isNew = true, ocupacionId = null) }
            }
        }
    }
}
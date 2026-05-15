package edu.ucne.registroocupaciones.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.ucne.registroocupaciones.domain.ocupacion.model.Ocupacion

@Composable
fun ListOcupacionScreen(
    viewModel: ListOcupacionViewModel = hiltViewModel(),
    onAddOcupacion: () -> Unit,
    onNavigateToEdit: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    OcupacionListBody(
        state = state,
        onEvent = { event ->
            when (event) {
                is ListOcupacionUiEvent.Edit -> onNavigateToEdit(event.id)
                ListOcupacionUiEvent.CreateNew -> onAddOcupacion()
                else -> viewModel.onEvent(event)
            }
        },
        onAddOcupacion = onAddOcupacion
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OcupacionListBody(
    state: ListOcupacionUiState,
    onEvent: (ListOcupacionUiEvent) -> Unit,
    onAddOcupacion: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(ListOcupacionUiEvent.CreateNew) }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar ocupacion"
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            } else {
                if (state.ocupaciones.isEmpty()) {
                    Text(
                        text = "No hay ocupaciones",
                        modifier = Modifier
                            .align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = state.ocupaciones,
                            key = { it.ocupacionId }
                        ) { ocupacion ->
                            OcupacionItem(
                                ocupacion = ocupacion,
                                onDelete = {
                                    onEvent(ListOcupacionUiEvent.Delete(ocupacion.ocupacionId))
                                },
                                onClick = {
                                    onEvent(ListOcupacionUiEvent.Edit(ocupacion.ocupacionId))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OcupacionItem(
    ocupacion: Ocupacion,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = ocupacion.descripcion,
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "DOP$ ${ocupacion.sueldo}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(
                onClick = onClick
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar ocupacion "
                )
            }
            IconButton(
                onClick = onDelete
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar ocupacion"
                )
            }
        }
    }
}
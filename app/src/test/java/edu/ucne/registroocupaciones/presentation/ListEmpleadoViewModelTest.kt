package edu.ucne.registroocupaciones.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import edu.ucne.registroocupaciones.domain.empleado.model.Empleado
import edu.ucne.registroocupaciones.domain.empleado.usecase.empleados.DeleteEmpleadoUseCase
import edu.ucne.registroocupaciones.domain.empleado.usecase.empleados.ObserveEmpleadoUseCase
import edu.ucne.registroocupaciones.presentation.empleado.list.ListEmpleadoUiEvent
import edu.ucne.registroocupaciones.presentation.empleado.list.ListEmpleadoViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.time.LocalDate

@ExperimentalCoroutinesApi
class ListEmpleadoViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ListEmpleadoViewModel
    private lateinit var observeEmpleadoUseCase: ObserveEmpleadoUseCase
    private lateinit var deleteEmpleadoUseCase: DeleteEmpleadoUseCase

    @Before
    fun setup() {
        observeEmpleadoUseCase = mockk()
        deleteEmpleadoUseCase = mockk()

        every { observeEmpleadoUseCase() } returns flowOf(emptyList())

        viewModel = ListEmpleadoViewModel(
            observeEmpleadoUseCase,
            deleteEmpleadoUseCase
        )
    }

    @Test
    fun `onLoad carga lista de empleados correctamente`() = runTest {
        // Given
        val empleados = listOf(
            Empleado(1, LocalDate.of(2024, 1, 15), "Juan Pérez", "M", "25000"),
            Empleado(2, LocalDate.of(2024, 3, 10), "María López", "F", "30000")
        )
        every { observeEmpleadoUseCase() } returns flowOf(empleados)

        // When
        viewModel = ListEmpleadoViewModel(observeEmpleadoUseCase, deleteEmpleadoUseCase)
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertEquals(2, viewModel.state.value.empleados.size)
        assertEquals("Juan Pérez", viewModel.state.value.empleados[0].nombres)
    }

    @Test
    fun `onEvent Delete elimina empleado`() = runTest {
        // Given
        val empleadoId = 1
        coEvery { deleteEmpleadoUseCase(empleadoId) } just Runs

        // When
        viewModel.onEvent(ListEmpleadoUiEvent.Delete(empleadoId))
        advanceUntilIdle()

        // Then
        coVerify { deleteEmpleadoUseCase(empleadoId) }
        assertEquals("Eliminado", viewModel.state.value.message)
    }

    @Test
    fun `onEvent CreateNew activa navegacion a crear`() {
        // When
        viewModel.onEvent(ListEmpleadoUiEvent.CreateNew)

        // Then
        assertTrue(viewModel.state.value.navigateToCreate)
    }

    @Test
    fun `onEvent Edit activa navegacion a editar con id`() {
        // Given
        val empleadoId = 5

        // When
        viewModel.onEvent(ListEmpleadoUiEvent.Edit(empleadoId))

        // Then
        assertEquals(empleadoId, viewModel.state.value.navigateToEditId)
    }

    @Test
    fun `onEvent ShowMessage muestra mensaje`() {
        // When
        viewModel.onEvent(ListEmpleadoUiEvent.ShowMessage("Test message"))

        // Then
        assertEquals("Test message", viewModel.state.value.message)
    }
}

@ExperimentalCoroutinesApi
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
package edu.ucne.registroocupaciones.domain.empleados.usecase

import edu.ucne.registroocupaciones.domain.empleado.model.Empleado
import edu.ucne.registroocupaciones.domain.empleado.repository.EmpleadoRepository
import edu.ucne.registroocupaciones.domain.empleado.usecase.empleados.ObserveEmpleadoUseCase
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

@ExperimentalCoroutinesApi
class ObserveEmpleadoUseCaseTest {
    private lateinit var useCase: ObserveEmpleadoUseCase
    private lateinit var repository: EmpleadoRepository

    @Before
    fun setup() {
        repository = mockk()
        useCase = ObserveEmpleadoUseCase(repository)
    }

    @Test
    fun `invoke retorna flow de empleados`() = runTest{
        // Given
        val empleados = listOf(
            Empleado(1, LocalDate.of(2026,5,16), "Adonis Mercado", "Masculino", "50000"),
            Empleado(2, LocalDate.of(2026,5,17), "Enel Almonte", "Masculino", "100000")
        )
        every { repository.observeEmpleados() } returns flowOf(empleados)

        // When
        val result = useCase().first()

        // Then
        assertEquals(2, result.size)
        assertEquals("Adonis Mercado", result[0].nombres)
        assertEquals("Enel Almonte", result[1].nombres)
        verify { repository.observeEmpleados() }
    }

    @Test
    fun `invoke retorna flow vacio cuando no hay empleados`() = runTest {
        // Given
        every { repository.observeEmpleados() } returns flowOf(emptyList())

        // When
        val result = useCase().first()

        // Then
        assertTrue(result.isEmpty())
        verify { repository.observeEmpleados() }
    }
}
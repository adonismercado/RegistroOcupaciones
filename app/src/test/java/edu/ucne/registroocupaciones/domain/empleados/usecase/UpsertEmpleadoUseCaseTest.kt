package edu.ucne.registroocupaciones.domain.empleados.usecase

import edu.ucne.registroocupaciones.domain.empleado.model.Empleado
import edu.ucne.registroocupaciones.domain.empleado.repository.EmpleadoRepository
import edu.ucne.registroocupaciones.domain.empleado.usecase.empleados.UpsertEmpleadoUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import org.junit.Assert.*

@ExperimentalCoroutinesApi
class UpsertEmpleadoUseCaseTest {
    private lateinit var useCase: UpsertEmpleadoUseCase
    private lateinit var repository: EmpleadoRepository

    @Before
    fun setup() {
        repository = mockk()
        useCase = UpsertEmpleadoUseCase(repository)
    }

    @Test
    fun `invoke guarda empleado con datos validos`() = runTest {
        // Given
        val empleado = Empleado(
            empleadoId = 0,
            fechaIngreso = LocalDate.of(2026,5,16),
            nombres = "Adonis Mercado",
            sexo = "Masculino",
            sueldo = "50000"
        )
        coEvery { repository.upsert(empleado) } returns 0

        // When
        val result = useCase(empleado)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(0, result.getOrNull())
        coVerify { repository.upsert(empleado) }
    }

    @Test
    fun `invoke falla con nombre vacio`() = runTest {
        // Given
        val empleado = Empleado(
            empleadoId = 0,
            fechaIngreso = LocalDate.of(2026,5,16),
            nombres = "",
            sexo = "Masculino",
            sueldo = "50000"
        )

        // When
        val result = useCase(empleado)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
        assertEquals("El nombre no puede estar vacio.", result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke falla con fecha nula`() = runTest {
        // Given
        val empleado = Empleado(
            empleadoId = 0,
            fechaIngreso = null,
            nombres = "Juan Pérez",
            sexo = "M",
            sueldo = "25000"
        )

        // When
        val result = useCase(empleado)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
        assertEquals("La fecha no puede estar vacia.", result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke falla con fecha futura`() = runTest {
        // Given
        val empleado = Empleado(
            empleadoId = 0,
            fechaIngreso = LocalDate.now().plusDays(1),
            nombres = "Juan Pérez",
            sexo = "M",
            sueldo = "25000"
        )

        // When
        val result = useCase(empleado)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
        assertEquals("La fecha no puede ser futura al dia de hoy.", result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke falla con sueldo vacio`() = runTest {
        // Given
        val empleado = Empleado(
            empleadoId = 0,
            fechaIngreso = LocalDate.of(2024, 1, 15),
            nombres = "Juan Pérez",
            sexo = "M",
            sueldo = ""
        )

        // When
        val result = useCase(empleado)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
        assertEquals("El sueldo no puede estar vacio.", result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke falla con sueldo no numerico`() = runTest {
        // Given
        val empleado = Empleado(
            empleadoId = 0,
            fechaIngreso = LocalDate.of(2024, 1, 15),
            nombres = "Juan Pérez",
            sexo = "M",
            sueldo = "abc"
        )

        // When
        val result = useCase(empleado)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
        assertEquals("El sueldo debe ser un numero.", result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke falla con sueldo menor o igual a cero`() = runTest {
        // Given
        val empleado = Empleado(
            empleadoId = 0,
            fechaIngreso = LocalDate.of(2024, 1, 15),
            nombres = "Juan Pérez",
            sexo = "M",
            sueldo = "-100"
        )

        // When
        val result = useCase(empleado)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
        assertEquals("El sueldo debe ser mayor que 0.", result.exceptionOrNull()?.message)
    }
}
package edu.ucne.registroocupaciones.domain.empleados.usecase

import edu.ucne.registroocupaciones.domain.empleado.model.Empleado
import edu.ucne.registroocupaciones.domain.empleado.repository.EmpleadoRepository
import edu.ucne.registroocupaciones.domain.empleado.usecase.empleados.GetEmpleadoUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

@ExperimentalCoroutinesApi
class GetEmpleadoUseCaseTest {
    private lateinit var useCase: GetEmpleadoUseCase
    private lateinit var repository: EmpleadoRepository

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetEmpleadoUseCase(repository)
    }

    @Test
    fun `invoke retorna empleado por id`() = runTest {
        // Given
        val empleado = Empleado(
            empleadoId = 1,
            fechaIngreso = LocalDate.of(2024, 1, 15),
            nombres = "Juan Pérez",
            sexo = "M",
            sueldo = "25000"
        )
        coEvery { repository.getEmpleado(1) } returns empleado

        // When
        val result = useCase(1)

        // Then
        assertNotNull(result)
        assertEquals("Juan Pérez", result?.nombres)
        assertEquals("25000", result?.sueldo)
        coVerify { repository.getEmpleado(1) }
    }

    @Test
    fun `invoke retorna null si empleado no existe`() = runTest {
        // Given
        coEvery { repository.getEmpleado(99) } returns null

        // When
        val result = useCase(99)

        // Then
        assertNull(result)
        coVerify { repository.getEmpleado(99) }
    }
}
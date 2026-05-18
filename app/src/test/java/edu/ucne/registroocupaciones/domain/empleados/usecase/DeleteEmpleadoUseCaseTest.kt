package edu.ucne.registroocupaciones.domain.empleados.usecase

import edu.ucne.registroocupaciones.domain.empleado.repository.EmpleadoRepository
import edu.ucne.registroocupaciones.domain.empleado.usecase.empleados.DeleteEmpleadoUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DeleteEmpleadoUseCaseTest {
    private lateinit var useCase: DeleteEmpleadoUseCase
    private lateinit var repository: EmpleadoRepository

    @Before
    fun setup() {
        repository = mockk()
        useCase = DeleteEmpleadoUseCase(repository)
    }

    @Test
    fun `invoke elimina empleado por id`() = runTest {
        // Given
        coEvery { repository.delete(1) } just Runs

        // When
        useCase(1)

        // Then
        coVerify { repository.delete(1) }
    }
}
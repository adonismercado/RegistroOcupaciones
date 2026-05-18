package edu.ucne.registroocupaciones.data.empleados.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import edu.ucne.registroocupaciones.data.empleados.dao.EmpleadoDao
import edu.ucne.registroocupaciones.data.empleados.entity.EmpleadoEntity
import edu.ucne.registroocupaciones.domain.empleado.model.Empleado
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@ExperimentalCoroutinesApi
class EmpleadoRepositoryImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: EmpleadoRepositoryImpl
    private lateinit var dao: EmpleadoDao

    @Before
    fun setup() {
        dao = mockk(relaxed = true)
        repository = EmpleadoRepositoryImpl(dao)
    }

    @Test
    fun `upsert guarda empleado correctamente`() = runTest {
        // Given
        val empleado = Empleado(
            empleadoId = 0,
            fechaIngreso = LocalDate.of(2026, 5, 15),
            nombres = "Adonis Mercado",
            sexo = "Masculino",
            sueldo = "50000"
        )
        val empleadoSlot = slot<EmpleadoEntity>()
        coEvery { dao.upsert(capture(empleadoSlot)) } just Runs

        // When
        val result = repository.upsert(empleado)

        assertEquals(0, result)
        coVerify { dao.upsert(any()) }
        assertEquals(empleado.nombres, empleadoSlot.captured.nombres)
        assertEquals(empleado.sexo, empleadoSlot.captured.sexo)
        assertEquals(empleado.sueldo, empleadoSlot.captured.sueldo)
    }

    @Test
    fun `upsert actualiza empleado correctamente`() = runTest {
        // Given
        val empleado = Empleado(
            empleadoId = 1,
            fechaIngreso = LocalDate.of(2026,3,22),
            nombres = "Adonis Mercado Actualizado",
            sexo = "Masculino",
            sueldo = "60000"
        )
        coEvery { dao.upsert(any()) } just Runs

        // When
        val result = repository.upsert(empleado)

        // Then
        assertEquals(1, result)
        coVerify { dao.upsert(any()) }
    }

    @Test
    fun `delete elimina empleado correctamente`() = runTest {
        // Given
        val empleadoId = 1
        coEvery { dao.deleteById(empleadoId) } just Runs

        // When
        repository.delete(empleadoId)

        // Then
        coVerify { dao.deleteById(empleadoId) }
    }

    @Test
    fun `observeEmpleados retorna flow de empleados`() = runTest {
        // Given
        val empleados = listOf(
            EmpleadoEntity(1, "2026-05-15", "Adonis Mercado", "Masculino", "50000"),
            EmpleadoEntity(2, "2026-05-16", "Enel Almonte", "Masculino", "100000")
        )
        every { dao.observeALl() } returns flowOf(empleados)

        // When
        val result = repository.observeEmpleados().first()

        // Then
        assertEquals(2, result.size)
        assertEquals("Adonis Mercado", result[0].nombres)
        assertEquals("Enel Almonte", result[1].nombres)
    }

    @Test
    fun `getEmpleado retorna empleado por id`() = runTest {
        // Given
        val empleado = EmpleadoEntity(1, "2026-05-17", "Adonis Mercado", "Masculino", "50000")
        coEvery { dao.getById(1) } returns empleado

        // When
        val result = repository.getEmpleado(1)

        // Then
        assertNotNull(result)
        assertEquals("Adonis Mercado", result?.nombres)
        assertEquals("50000", result?.sueldo)
    }
}
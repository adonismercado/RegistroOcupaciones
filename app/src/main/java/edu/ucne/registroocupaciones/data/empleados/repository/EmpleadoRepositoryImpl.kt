package edu.ucne.registroocupaciones.data.empleados.repository

import edu.ucne.registroocupaciones.data.empleados.dao.EmpleadoDao
import edu.ucne.registroocupaciones.data.empleados.mapper.toDomain
import edu.ucne.registroocupaciones.data.empleados.mapper.toEntity
import edu.ucne.registroocupaciones.domain.empleado.model.Empleado
import edu.ucne.registroocupaciones.domain.empleado.repository.EmpleadoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class EmpleadoRepositoryImpl @Inject constructor(
    private val dao: EmpleadoDao
) : EmpleadoRepository {
    override fun observeEmpleados(): Flow<List<Empleado>> = dao.observeALl().map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun getEmpleado(id: Int): Empleado? = dao.getById(id)?.toDomain()

    override suspend fun upsert(empleado: Empleado): Int {
        dao.upsert(empleado.toEntity())
        return empleado.empleadoId
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }
}
package edu.ucne.registroocupaciones.data.ocupaciones.repository

import edu.ucne.registroocupaciones.data.ocupaciones.local.daos.EmpleadoDao
import edu.ucne.registroocupaciones.domain.ocupacion.model.Empleado
import edu.ucne.registroocupaciones.domain.ocupacion.repository.EmpleadoRepository
import edu.ucne.registroocupaciones.data.ocupaciones.mapper.toDomain
import edu.ucne.registroocupaciones.data.ocupaciones.mapper.toEntity
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

    override suspend fun filterByName(nombre: String): Flow<List<Empleado>> = dao.filterByName(nombre).map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun filterByDate(fecha: LocalDate): Flow<List<Empleado>> = dao.filterByDate(fecha).map { list ->
        list.map { it.toDomain() }
    }
}
package edu.ucne.registroocupaciones.data.ocupaciones.repository

import edu.ucne.registroocupaciones.data.ocupaciones.local.daos.OcupacionDao
import edu.ucne.registroocupaciones.data.ocupaciones.mapper.toDomain
import edu.ucne.registroocupaciones.data.ocupaciones.mapper.toEntity
import edu.ucne.registroocupaciones.domain.ocupacion.model.Ocupacion
import edu.ucne.registroocupaciones.domain.ocupacion.repository.OcupacionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OcupacionRepositoryImpl @Inject constructor(
    private val dao: OcupacionDao
) : OcupacionRepository {
    override fun observeOcupaciones(): Flow<List<Ocupacion>> = dao.observeAll().map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun getOcupacion(id: Int): Ocupacion? = dao.getById(id)?.toDomain()

    override suspend fun upsert(ocupacion: Ocupacion): Int {
        dao.upsert(ocupacion.toEntity())
        return ocupacion.ocupacionId
    }

    override suspend fun delete(id: Int){
        dao.deleteById(id)
    }

    override suspend fun existeDescripcion(descripcion: String, id: Int): Boolean {
        return dao.existeDescripcion(descripcion, id)
    }
}